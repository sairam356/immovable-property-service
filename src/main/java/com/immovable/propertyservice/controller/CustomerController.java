package com.immovable.propertyservice.controller;

import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import com.immovable.propertyservice.entities.Customer;
import com.immovable.propertyservice.services.CustomerService;

@RestController
@RequestMapping("/customer")
public class CustomerController {

	@Autowired
	CustomerService customerService;

	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public Map<String,String> saveCustomer(@RequestBody Customer customer) {
		return customerService.saveCustomer(customer);

	}

	@PutMapping("/updateCustomerStake")
	public Map<String, String>updateCustomerStake(@RequestBody Customer customer){
		return customerService.updateCustomerStake(customer);
	}
	@GetMapping("/investment")
	public Map<String, BigDecimal> getCustomerInvestment(@PathVariable("customerId") String customerId){
		return customerService.getInvestedAmount(customerId);
	}

}
