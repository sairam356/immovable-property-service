package com.immovable.propertyservice.dto;

import java.util.List;

import com.immovable.propertyservice.entities.Wallet;

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
	
	private Wallet wallet;
	
	private CartResponseDto cart;
	
	private int propertyCounter;
}
