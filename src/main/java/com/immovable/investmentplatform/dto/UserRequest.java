package com.immovable.investmentplatform.dto;

import lombok.Data;

@Data
public class UserRequest {

	private String username;
	private String password;
	private String firstName;
	private String address;
	private String passport;
	private String lastName;
	private String email;
	private String phone;
	private Boolean isLoanRequired;
	private String role;
	
}
