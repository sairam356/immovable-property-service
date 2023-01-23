package com.immovable.investmentplatform.services;

import java.math.BigDecimal;
import java.util.Map;

import com.immovable.investmentplatform.dto.CustomerResponseDto;
import com.immovable.investmentplatform.dto.CustomerStakeDTO;
import com.immovable.investmentplatform.entities.Customer;

public interface CustomerService {

	public Map<String, String> saveCustomer(Customer customer);

	Map<String, BigDecimal> getInvestedAmount(String customerId);

	CustomerResponseDto retrieveCustomerDetailsById(String customerId);

	Map<String, String> updateCustomerStake(CustomerStakeDTO customerStakeDTO);

	Map<String, String> getLoanDetails(String customerId);
	
	Map<String, String> updateRevenueInfo(String customerId);

	public CustomerResponseDto retrieveCustomerDetailsByIdAndStackId(String customerId, String stackId);

}
