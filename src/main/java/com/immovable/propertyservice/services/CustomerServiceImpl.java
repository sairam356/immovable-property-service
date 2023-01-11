package com.immovable.propertyservice.services;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;

import com.immovable.propertyservice.entities.Customer;
import com.immovable.propertyservice.repo.CustomerRepoistory;

public class CustomerServiceImpl implements CustomerService {

	@Autowired
	CustomerRepoistory custRepository;

	@Override
	public Map<String, String> saveCustomer(Customer customer) {
		Map<String, String> respMap = new HashMap<>();
		try {
			custRepository.save(customer);
			respMap.put("status", "success");

		} catch (Exception e) {
			respMap.put("status", "failure");
		}
		return respMap;
	}

}
