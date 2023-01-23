package com.immovable.investmentplatform.dto;

import org.springframework.security.core.userdetails.User;

import com.immovable.investmentplatform.entities.UserEntity;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper=false)
public class CustomUser extends User {
	
	private static final long serialVersionUID = 1L;

	public CustomUser(UserEntity user) {
		super(user.getUsername(), user.getPassword(), user.getGrantedAuthoritiesList());
	}
}