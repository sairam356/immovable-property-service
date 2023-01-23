package com.immovable.investmentplatform.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import lombok.Data;

@Data
@JsonInclude(value = Include.NON_NULL)
public class UserResponse {

	private String id;
	private String customerId;
	private String username;
	private String password;
	private String passport;
	private String address;
	private String firstName;
	private String lastName;
	private String email;
	private String phone;
	private boolean active;
	
}
