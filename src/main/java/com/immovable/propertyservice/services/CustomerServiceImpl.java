package com.immovable.propertyservice.services;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.immovable.propertyservice.dto.WalletDto;
import com.immovable.propertyservice.entities.CustomerPropertiesStack;
import com.immovable.propertyservice.entities.CustomerRevenue;
import com.immovable.propertyservice.enums.ResourceType;
import com.immovable.propertyservice.exception.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.immovable.propertyservice.entities.Customer;
import com.immovable.propertyservice.entities.Wallet;
import com.immovable.propertyservice.repo.CustomerRepoistory;
import org.springframework.util.CollectionUtils;

@Service
public class CustomerServiceImpl implements CustomerService {

	@Autowired
	CustomerRepoistory custRepository;

	@Autowired
	private WalletService walletService;

	@Override
	@Transactional
	public Map<String, String> saveCustomer(Customer customer) {
		Map<String, String> respMap = new HashMap<>();
		try {

			Customer saveCustomerObj = saveCustomerObj(customer);

			Wallet walletObj = getWalletObj(saveCustomerObj);
			
			saveCustomerObj.setWallet(walletObj);
			
			custRepository.save(saveCustomerObj);
			
			respMap.put("status", "success");

		} catch (Exception e) {
			respMap.put("status", "failure");
		}
		return respMap;
	}

	@Override
	public Map<String, BigDecimal> getInvestedAmount(String customerId) {
		 Customer customer = custRepository.findById(customerId).orElseThrow(() -> new ResourceNotFoundException(ResourceType.CUSTOMER));
		 List<CustomerPropertiesStack> revenues = customer.getPropertiesStack();
		 if(!CollectionUtils.isEmpty(revenues)){
			 new HashMap<String, BigDecimal>().put("investedAmount",revenues.parallelStream().map(CustomerPropertiesStack::getInvestedamount).reduce(
					 BigDecimal.ZERO, BigDecimal::add));;
		 }
		 return Collections.EMPTY_MAP;
	}

	@Override
	public Map<String, String> updateCustomerStake(Customer customer) {
		custRepository.findById(customer.getId()).orElseThrow(() -> new ResourceNotFoundException(ResourceType.CUSTOMER));
		return null;
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
