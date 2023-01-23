package com.immovable.investmentplatform.services;

import com.immovable.investmentplatform.dto.UserRequest;
import com.immovable.investmentplatform.dto.UserResponse;
import com.immovable.investmentplatform.entities.UserEntity;

public interface UserService {

	UserResponse findUserByUserName(String userName);

	boolean loginUser(String username, String password);

	UserEntity createUser(UserRequest request);

	boolean checkUserExists(String username, String email);
	
	String resetPassword(String email);
	
	UserResponse updateUser(UserEntity request);
	

}
