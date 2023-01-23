package com.immovable.investmentplatform.dto;

import java.math.BigDecimal;
import java.util.List;

import com.immovable.investmentplatform.entities.CustomerRevenue;
import com.immovable.investmentplatform.entities.Wallet;

import lombok.Data;

@Data
public class CustomerResponseDto {

	private String id;
	private String firstName;
	private String lastName;
	private String passport;
	private String address;
	private String userId;
	
	private List<PropertiesStack> propertyStack;
	
	private List<CustomerRevenue> customerRevenue;
	
	private Wallet wallet;
	
	private CartResponseDto cart;
	
	private int propertyCounter;
	
	private BigDecimal totalInvestAmount;
	
	private BigDecimal totalEarning;
	
	private BigDecimal annualizedEarning;
}
