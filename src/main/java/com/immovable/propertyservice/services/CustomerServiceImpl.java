package com.immovable.propertyservice.services;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import com.immovable.propertyservice.dto.CartUpdateRequestDto;
import com.immovable.propertyservice.dto.CustomerResponseDto;
import com.immovable.propertyservice.dto.CustomerStakeDTO;
import com.immovable.propertyservice.dto.PropertiesStack;
import com.immovable.propertyservice.dto.PropertyResponseDTO;
import com.immovable.propertyservice.dto.PropertyStakeReqDTO;
import com.immovable.propertyservice.dto.WalletDto;
import com.immovable.propertyservice.entities.Customer;
import com.immovable.propertyservice.entities.CustomerPropertiesStack;
import com.immovable.propertyservice.entities.Wallet;
import com.immovable.propertyservice.enums.ResourceType;
import com.immovable.propertyservice.exception.ResourceNotFoundException;
import com.immovable.propertyservice.repo.CustomerPropertiesStackRepoistory;
import com.immovable.propertyservice.repo.CustomerRepoistory;

@Service
public class CustomerServiceImpl implements CustomerService {

	@Autowired
	private CustomerRepoistory custRepository;

	@Autowired
	private CustomerPropertiesStackRepoistory propertyStackRepository;

	@Autowired
	private WalletService walletService;

	@Autowired
	private PropertyService propertyService;

	@Autowired
	private CartService cartService;

	@Override
	@Transactional
	public Map<String, String> saveCustomer(Customer customer) {
		Map<String, String> respMap = new HashMap<>();
		try {

			Customer saveCustomerObj = saveCustomerObj(customer);

			Wallet walletObj = getWalletObj(saveCustomerObj);

			saveCustomerObj.setWallet(walletObj);

			Customer custResponse = custRepository.save(saveCustomerObj);

			respMap.put("status", "success");
			respMap.put("customerId", custResponse.getId());

		} catch (Exception e) {
			respMap.put("status", "failure");
		}
		return respMap;
	}

	@Override
	public Map<String, BigDecimal> getInvestedAmount(String customerId) {
		Customer customer = custRepository.findById(customerId)
				.orElseThrow(() -> new ResourceNotFoundException(ResourceType.CUSTOMER));
		List<CustomerPropertiesStack> revenues = customer.getPropertiesStack();
		if (!CollectionUtils.isEmpty(revenues)) {
			new HashMap<String, BigDecimal>().put("investedAmount", revenues.parallelStream()
					.map(CustomerPropertiesStack::getInvestedamount).reduce(BigDecimal.ZERO, BigDecimal::add));
			;
		}
		return Collections.EMPTY_MAP;
	}

	@Override
	public Map<String, String> updateCustomerStake(CustomerStakeDTO customerStakeDTO) {

		Map<String, String> map = new HashMap<>();
		try {
			Optional<Customer> custObj = custRepository.findById(customerStakeDTO.getCustomerId());
			if (custObj.isPresent()) {
				boolean updatePropertyStake = updatePropertyStake(customerStakeDTO.getPropertyStakeReDTO());

				if (updatePropertyStake) {

					CartUpdateRequestDto requestDTO = new CartUpdateRequestDto();
					requestDTO.setCustomerId(custObj.get().getId());

					cartService.cartUpdateOnPaymentStatus(requestDTO, "INACTIVE");

					map.put("status", "Success");

				} else {
					map.put("status", "Failure");
				}
			}

		} catch (Exception e) {
			map.put("status", "Failure");
			e.printStackTrace();

		}
		return map;

	}

	public CustomerResponseDto retrieveCustomerDetailsById(String customerId) {

		CustomerResponseDto customerResponse = new CustomerResponseDto();

		Optional<Customer> customer = custRepository.findById(customerId);
		Customer customerObj = customer.get();
		customerResponse.setId(customerObj.getId());
		customerResponse.setFirstName(customerObj.getFirstName());
		customerResponse.setLastName(customerObj.getLastName());
		customerResponse.setPassport(customerObj.getPassport());
		customerResponse.setAddress(customerObj.getAddress());
		customerResponse.setUserId(customerObj.getUserId());

		List<CustomerPropertiesStack> propertiesStack = propertyStackRepository.findAllByCustomerId(customerId);

		customerObj.setPropertiesStack(propertiesStack);

		if (customer.isPresent() && !CollectionUtils.isEmpty(customerObj.getPropertiesStack())) {

			List<PropertiesStack> propertyLst = new ArrayList<>();

			for (CustomerPropertiesStack property : customerObj.getPropertiesStack()) {
				PropertiesStack prop = mapPropertyToCustomer(property);
				propertyLst.add(prop);
			}
			customerResponse.setPropertyStack(propertyLst);
			customerResponse.setPropertyCounter(propertyLst.size());
		}
		customerResponse.setWallet(walletService.getWalletInfo(customerId));

		customerResponse.setCart(cartService.getCartDetails(customerId));

		return customerResponse;
	}

	private PropertiesStack mapPropertyToCustomer(CustomerPropertiesStack customerPropertystack) {

		String propertyId = customerPropertystack.getPropertyId();
		PropertyResponseDTO propertyDto = propertyService.getPropertyById(propertyId);

		if (propertyDto != null) {

			PropertiesStack propertiesStack = new PropertiesStack();
			propertiesStack.setCreatedDate(customerPropertystack.getCreatedDate());
			propertiesStack.setStakepercentage(customerPropertystack.getStakepercentage());
			propertiesStack.setInvestedamount(customerPropertystack.getInvestedamount());
			propertiesStack.setProperty(propertyDto.getProperty());
			propertiesStack.setId(customerPropertystack.getId());

			return propertiesStack;
		}
		return null;
	}

	private boolean updatePropertyStake(List<PropertyStakeReqDTO> propertyStakeReDTO) {
		try {
			propertyStakeReDTO.forEach(pstkObj -> {
				Map<String, BigDecimal> updateStakeInfo = propertyService.updateStakeInfo(pstkObj);

				/*
				 * 
				 * update customer stak code .
				 */

			});
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	public Wallet getWalletObj(Customer customer) {
		WalletDto wallet = new WalletDto();
		wallet.setCustomerId(customer.getId());
		return walletService.save(wallet);
	}

	@Transactional(propagation = Propagation.REQUIRES_NEW)
	public Customer saveCustomerObj(Customer customer) {
		Customer custobj = custRepository.save(customer);

		return custobj;
	}

}
