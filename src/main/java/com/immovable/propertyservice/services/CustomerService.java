package com.immovable.propertyservice.services;

import java.math.BigDecimal;
import java.util.Map;

import com.immovable.propertyservice.entities.Customer;

public interface CustomerService {

	 public Map<String ,String> saveCustomer(Customer customer);

    Map<String, BigDecimal> getInvestedAmount(String customerId);

    Map<String, String> updateCustomerStake(Customer customer);
}
