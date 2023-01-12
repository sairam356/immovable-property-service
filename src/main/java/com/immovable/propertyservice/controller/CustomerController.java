package com.immovable.propertyservice.controller;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

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

}
