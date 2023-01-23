package com.immovable.investmentplatform.controller;

import java.math.BigDecimal;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.immovable.investmentplatform.dto.CustomerResponseDto;
import com.immovable.investmentplatform.dto.CustomerStakeDTO;
import com.immovable.investmentplatform.entities.Customer;
import com.immovable.investmentplatform.entities.CustomerPropertiesStack;
import com.immovable.investmentplatform.repos.CustomerPropertiesStackRepoistory;
import com.immovable.investmentplatform.services.CustomerService;

@RestController
@RequestMapping("/customer")
public class CustomerController {

	@Autowired
	CustomerService customerService;

	@Autowired
	CustomerPropertiesStackRepoistory repo;

	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public Map<String, String> saveCustomer(@RequestBody Customer customer) {
		return customerService.saveCustomer(customer);

	}

	@GetMapping("/{customerId}/{stakeId}")
	public CustomerResponseDto getCustomerById(@PathVariable("customerId")  String customerId, @PathVariable("stakeId") String stakeId) {

		return customerService.retrieveCustomerDetailsByIdAndStackId(customerId,stakeId);
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
	public Map<String, String> updateCustomerStake(@RequestBody CustomerStakeDTO customerStakeDTO) {
		return customerService.updateCustomerStake(customerStakeDTO);
	}

	@GetMapping("/investment/{custObj}")
	public Map<String, BigDecimal> getCustomerInvestment(@PathVariable("custObj") String customerId) {
		return customerService.getInvestedAmount(customerId);
	}

	@GetMapping("/loan/{custId}")
	public Map<String, String> getCustomerLoanDetails(@PathVariable("custId") String customerId) {
		System.out.println("controller");
		return customerService.getLoanDetails(customerId);
	}
	
	@GetMapping("revenu/{custId}")
	public Map<String,String>  setUpRevenue(@PathVariable("custId") String customerId){
		return customerService.updateRevenueInfo(customerId);
	}

}
