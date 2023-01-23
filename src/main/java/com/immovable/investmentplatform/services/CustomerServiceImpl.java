package com.immovable.investmentplatform.services;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import org.springframework.web.client.RestTemplate;

import com.immovable.investmentplatform.dto.CartUpdateRequestDto;
import com.immovable.investmentplatform.dto.CustomerResponseDto;
import com.immovable.investmentplatform.dto.CustomerStakeDTO;
import com.immovable.investmentplatform.dto.PropertiesStack;
import com.immovable.investmentplatform.dto.PropertyResponseDTO;
import com.immovable.investmentplatform.dto.PropertyStakeReqDTO;
import com.immovable.investmentplatform.dto.TranscationRequest;
import com.immovable.investmentplatform.dto.UserRequest;
import com.immovable.investmentplatform.dto.WalletDto;
import com.immovable.investmentplatform.dto.WalletTransactionDto;
import com.immovable.investmentplatform.entities.Customer;
import com.immovable.investmentplatform.entities.CustomerPropertiesStack;
import com.immovable.investmentplatform.entities.CustomerRevenue;
import com.immovable.investmentplatform.entities.UserEntity;
import com.immovable.investmentplatform.entities.Wallet;
import com.immovable.investmentplatform.enums.ResourceType;
import com.immovable.investmentplatform.enums.TransactionStatus;
import com.immovable.investmentplatform.enums.TransactionType;
import com.immovable.investmentplatform.exceptions.ResourceNotFoundException;
import com.immovable.investmentplatform.repos.CustomerPropertiesStackRepoistory;
import com.immovable.investmentplatform.repos.CustomerRepoistory;
import com.immovable.investmentplatform.repos.CustomerRevenueRepoistory;
import com.immovable.investmentplatform.repos.UserRepository;

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

	@Autowired
	private UserRepository userRepo;

	@Autowired
	private CustomerPropertiesStackRepoistory customerPropertiesStackRepoistory;
	
	@Autowired
	private CustomerRevenueRepoistory revenuRepo;

	@Value("${app.banking-service.url}")
	private String baseUrl;
	@Autowired
	private RestTemplate restTemplate;

	@Override
	@Transactional
	public Map<String, String> saveCustomer(Customer customer) {
		Map<String, String> respMap = new HashMap<>();
		try {

			List<Customer> findByUserId = custRepository.findByUserId(customer.getUserId());
			if (findByUserId.size() > 0) {

				respMap.put("status", "success");
				respMap.put("customerId", findByUserId.get(0).getId());

			} else {
				Customer saveCustomerObj = saveCustomerObj(customer);

				Wallet walletObj = getWalletObj(saveCustomerObj);

				saveCustomerObj.setWallet(walletObj);

				Customer custResponse = custRepository.save(saveCustomerObj);

				respMap.put("status", "success");
				respMap.put("customerId", custResponse.getId());

			}

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
		Optional<Customer> custObj = custRepository.findById(customerStakeDTO.getCustomerId());

		String userId = custObj.get().getUserId();
		String phnNumber = userRepo.findById(userId).get().getPhone();

		if (verifyWalletAmount(customerStakeDTO, phnNumber)) {
			try {

				if (custObj.isPresent()) {
					// debit or credit --> based on type buy or sell
					List<CustomerPropertiesStack> updatePropertyStake = updatePropertyStake(
							customerStakeDTO.getPropertyStakeReDTO(), customerStakeDTO.getBankName(), phnNumber);

					if (updatePropertyStake.size() > 0) {

						CartUpdateRequestDto requestDTO = new CartUpdateRequestDto();
						requestDTO.setCustomerId(custObj.get().getId());

						cartService.cartUpdateOnPaymentStatus(requestDTO, "INACTIVE");

						saveCustomerStakeList(custObj.get(), updatePropertyStake);

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
		} else {
			map.put("status", "Failure");
			return map;
		}
	}

	public String doTranscation(PropertyStakeReqDTO propertyStakeReqDTO, String bankName, String phnNumber) {

		TranscationRequest transcationRequest = new TranscationRequest();

		transcationRequest.setType(propertyStakeReqDTO.getTranscationType());

		transcationRequest.setAmount(propertyStakeReqDTO.getInvestmentAmount());
		transcationRequest.setBankName(bankName);
		transcationRequest.setPhoneNumber(phnNumber);
		HttpHeaders headers = new HttpHeaders();
		headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
		HttpEntity<TranscationRequest> entity = new HttpEntity<TranscationRequest>(transcationRequest, headers);
		return restTemplate.exchange(baseUrl + "/transcation", HttpMethod.POST, entity, String.class).getBody();
	}

	private boolean verifyWalletAmount(CustomerStakeDTO customerStakeDTO, String phnNumber) {

		TranscationRequest transcationRequest = new TranscationRequest();

		HttpHeaders headers = new HttpHeaders();
		headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));

		BigDecimal totalTranscationAmount = customerStakeDTO.getPropertyStakeReDTO().stream()
				.filter(y -> y.getTranscationType().equalsIgnoreCase("BUY")).map(x -> x.getInvestmentAmount())
				.reduce(BigDecimal.ZERO, BigDecimal::add);

		transcationRequest.setCartAmount(totalTranscationAmount);
		transcationRequest.setBankName(customerStakeDTO.getBankName());
		transcationRequest.setPhoneNumber(phnNumber);
		HttpEntity<TranscationRequest> entity = new HttpEntity<TranscationRequest>(transcationRequest, headers);

		Map body = restTemplate.exchange(baseUrl + "/verify", HttpMethod.POST, entity, Map.class).getBody();
		System.out.println(body.toString());
		return true;
	}

	public CustomerResponseDto retrieveCustomerDetailsByIdAndStackId(String customerId, String stackId) {

		CustomerResponseDto customerResponse = new CustomerResponseDto();

		Optional<Customer> customer = custRepository.findById(customerId);
		Customer customerObj = customer.get();
		customerResponse.setId(customerObj.getId());
		customerResponse.setFirstName(customerObj.getFirstName());
		customerResponse.setLastName(customerObj.getLastName());
		customerResponse.setPassport(customerObj.getPassport());
		customerResponse.setAddress(customerObj.getAddress());
		customerResponse.setUserId(customerObj.getUserId());

		List<PropertiesStack> propertyLst = new ArrayList<>();

		if (customer.isPresent() && !CollectionUtils.isEmpty(customerObj.getPropertiesStack())) {

			for (CustomerPropertiesStack property : customerObj.getPropertiesStack()) {
				if (property.getId().equals(stackId)) {
					PropertiesStack prop = mapPropertyToCustomer(property);
					propertyLst.add(prop);
				}
			}

		}
		BigDecimal totalInvestAmount, retails;
		if (Optional.ofNullable(customerObj.getCustomerRevenue()).isPresent()) {
			retails = customerObj.getCustomerRevenue().stream().map(x -> x.getActualAmount()).reduce(BigDecimal.ZERO,
					BigDecimal::add);
		} else {
			retails = new BigDecimal("0.00");
		}
		customerResponse.setCustomerRevenue(customerObj.getCustomerRevenue());

		customerResponse.setAnnualizedEarning(retails);

		customerResponse.setPropertyStack(propertyLst);
		return customerResponse;

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

		List<PropertiesStack> propertyLst = new ArrayList<>();

		if (customer.isPresent() && !CollectionUtils.isEmpty(customerObj.getPropertiesStack())) {

			for (CustomerPropertiesStack property : customerObj.getPropertiesStack()) {
				if (property.getStatus()!=null && property.getStatus().equals("ACTIVE")) {
					PropertiesStack prop = mapPropertyToCustomer(property);
					propertyLst.add(prop);
				}
			}

		}
		BigDecimal totalInvestAmount, retails;
		if (Optional.ofNullable(customerObj.getPropertiesStack()).isPresent()) {
			totalInvestAmount = customerObj.getPropertiesStack().stream().map(x -> x.getInvestedamount())
					.reduce(BigDecimal.ZERO, BigDecimal::add);
		} else {
			totalInvestAmount = new BigDecimal("0.00");
		}

		if (Optional.ofNullable(customerObj.getCustomerRevenue()).isPresent()) {
			retails = customerObj.getCustomerRevenue().stream().map(x -> x.getActualAmount()).reduce(BigDecimal.ZERO,
					BigDecimal::add);
		} else {
			retails = new BigDecimal("0.00");
		}
		customerResponse.setPropertyStack(propertyLst);
		customerResponse.setPropertyCounter(propertyLst.size());
		customerResponse.setWallet(walletService.getWalletInfo(customerId));

		customerResponse.setCart(cartService.getCartDetails(customerId));

		customerResponse.setAnnualizedEarning(retails);

		customerResponse.setCustomerRevenue(customerObj.getCustomerRevenue());

		customerResponse.setTotalInvestAmount(totalInvestAmount);

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

	private List<CustomerPropertiesStack> updatePropertyStake(List<PropertyStakeReqDTO> propertyStakeReDTO,
			String banKName, String phnNum) {
		List<CustomerPropertiesStack> listobj = new ArrayList<>();
		try {
			propertyStakeReDTO.forEach(pstkObj -> {
				Map<String, BigDecimal> updateStakeInfo = propertyService.updateStakeInfo(pstkObj);
				saveTranscation(pstkObj);

				listobj.add(saveCustomerStake(pstkObj, updateStakeInfo));
				doTranscation(pstkObj, banKName, phnNum);
			});

			return listobj;
		} catch (Exception e) {
			e.printStackTrace();
			return listobj;
		}
	}

	private void saveTranscation(PropertyStakeReqDTO propertyStakeReqDTO) {

		WalletTransactionDto dto = new WalletTransactionDto();
		dto.setCustomerId(propertyStakeReqDTO.getCustomerId());
		dto.setTransactionType(TransactionType.valueOf(propertyStakeReqDTO.getTranscationType()));
		dto.setTransactionStatus(TransactionStatus.SUCCESS);
		dto.setAmount(propertyStakeReqDTO.getInvestmentAmount());

		walletService.saveTransaction(dto);
	}

	private CustomerPropertiesStack saveCustomerStake(PropertyStakeReqDTO pstkObj,
			Map<String, BigDecimal> updateStakeInfo) {
		CustomerPropertiesStack customerPropertiesStack =null;
		if (pstkObj.getTranscationType().equals("BUY")) {
			customerPropertiesStack  = new CustomerPropertiesStack();
			customerPropertiesStack.setPropertyId(pstkObj.getPropertyId());
			customerPropertiesStack.setStakepercentage(updateStakeInfo.get("customerStack").multiply(new BigDecimal("100")));
			customerPropertiesStack.setInvestedamount(pstkObj.getInvestmentAmount());
			customerPropertiesStack.setCreatedDate(new Date());
			customerPropertiesStack.setCustomerId(pstkObj.getCustomerId());
			customerPropertiesStack.setStatus("ACTIVE");
		} else {
			List<CustomerPropertiesStack> findByPropertyIdAndStatus = customerPropertiesStackRepoistory.findByPropertyIdAndCustomerIdAndStatus(pstkObj.getPropertyId(),pstkObj.getCustomerId(),"ACTIVE");
			
			 customerPropertiesStack = findByPropertyIdAndStatus.get(0);
			customerPropertiesStack.setInvestedamount(new BigDecimal("0.00"));
			customerPropertiesStack.setStatus("INACTIVE");
		}
		return customerPropertiesStackRepoistory.save(customerPropertiesStack);
	}

	private void saveCustomerStakeList(Customer custObj, List<CustomerPropertiesStack> updatePropertyStake) {

		List<CustomerPropertiesStack> propertiesStack = null;
		if (Optional.ofNullable(custObj.getPropertiesStack()).isPresent()) {
			propertiesStack = custObj.getPropertiesStack();
			propertiesStack.addAll(updatePropertyStake);
		} else {
			custObj.setPropertiesStack(updatePropertyStake);
		}
		custRepository.save(custObj);
	}

	public Wallet getWalletObj(Customer customer) {
		WalletDto wallet = new WalletDto();
		wallet.setCustomerId(customer.getId());
		wallet.setBalance(new BigDecimal("10000000000"));
		return walletService.save(wallet);
	}

	@Transactional(propagation = Propagation.REQUIRES_NEW)
	public Customer saveCustomerObj(Customer customer) {
		Customer custobj = custRepository.save(customer);

		return custobj;
	}

	@Override
	public Map<String, String> getLoanDetails(String customerId) {

		System.out.println(" #################################");
		Map<String, String> map = new HashMap<>();
		Customer customer = custRepository.findById(customerId)
				.orElseThrow(() -> new ResourceNotFoundException(ResourceType.CUSTOMER));
		Optional<UserEntity> userEntity = userRepo.findById(customer.getUserId());
		UserRequest request = new UserRequest();
		request.setIsLoanRequired(true);
		request.setAddress(userEntity.get().getAddress());
		request.setEmail(userEntity.get().getEmail());
		request.setPhone(userEntity.get().getPhone());
		request.setFirstName(userEntity.get().getFirstName());
		request.setLastName(userEntity.get().getLastName());
		request.setUsername(userEntity.get().getUsername());
		callBankService(request);

		customer.setIsLoanTaken(true);
		custRepository.save(customer);

		map.put("status", "Success");
		return map;
	}

	private void callBankService(UserRequest request) {
		System.out.println(request.toString());
		HttpHeaders headers = new HttpHeaders();
		headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
		HttpEntity<UserRequest> req = new HttpEntity<UserRequest>(request, headers);
		restTemplate.exchange(baseUrl + "/loanUpdate", HttpMethod.POST, req, Map.class);
	}

	@Override
	public Map<String, String> updateRevenueInfo(String customerId) {
		
		Customer customer = custRepository.findById(customerId)
				.orElseThrow(() -> new ResourceNotFoundException(ResourceType.CUSTOMER));
		
		CustomerPropertiesStack customerPropertiesStack = customer.getPropertiesStack().get(0);
		
		BigDecimal stakepercentage = customerPropertiesStack.getStakepercentage();
		
		BigDecimal stakeAMont = customerPropertiesStack.getInvestedamount();
		
		                         
		PropertyResponseDTO propertyDto = propertyService.getPropertyById( customerPropertiesStack.getPropertyId());
		BigDecimal totalInvestmentCost = propertyDto.getProperty().getTotalInvestmentCost();
		
		CustomerRevenue  custRev = new CustomerRevenue();
		custRev.setMonth("DEC");
		custRev.setYear("2022");
		custRev.setActualAmount(new BigDecimal("0.00"));
		custRev.setSentAmount(new BigDecimal("0.00"));
		custRev.setPropertyId( customerPropertiesStack.getPropertyId());
		
		List<CustomerRevenue> l = new ArrayList<CustomerRevenue>();
		
		 l.add(revenuRepo.save(custRev));
		 customer.setCustomerRevenue(l);
		 
		 custRepository.save(customer);
		return null;
	}
}
