package com.immovable.propertyservice.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;

import com.immovable.propertyservice.dto.CustomerResponseDto;
import com.immovable.propertyservice.dto.CustomerStakeDTO;
import com.immovable.propertyservice.entities.Customer;
import com.immovable.propertyservice.entities.CustomerPropertiesStack;
import com.immovable.propertyservice.repo.CustomerPropertiesStackRepoistory;
import com.immovable.propertyservice.services.CustomerService;

@RestController
@RequestMapping("/customer")
public class CustomerController {

	@Autowired
	CustomerService customerService;
	
	@Autowired
	CustomerPropertiesStackRepoistory repo;

	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public Map<String,String> saveCustomer(@RequestBody Customer customer) {
		return customerService.saveCustomer(customer);

	}
	
	@GetMapping("/{customerId}")
	public CustomerResponseDto getCustomerById(@PathVariable String customerId) {
		
		return customerService.retrieveCustomerDetailsById(customerId);
	}
	
	@PostMapping("/propstack")
	public CustomerPropertiesStack saveCustPropStack(@RequestBody CustomerPropertiesStack request) {
		
		return repo.save(request);
	}

	@PutMapping("/updateCustomerStake")
	public Map<String, String>updateCustomerStake(@RequestBody CustomerStakeDTO customerStakeDTO){
		return customerService.updateCustomerStake(customerStakeDTO);
	}
	@GetMapping("/investment")
	public Map<String, BigDecimal> getCustomerInvestment(@PathVariable("customerId") String customerId){
		return customerService.getInvestedAmount(customerId);
	}

}
