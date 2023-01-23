package com.immovable.investmentplatform.services;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Service;

import com.immovable.investmentplatform.repos.UserRepository;

@Service
public class AdminServiceImpl implements AdminService {
	
	@Autowired
	private UserRepository repository;

	@Override
	public String sayHello() {
		return "Hello !!";
	}

	@Override
	public String deleteUser(String id) {
		
		repository.deleteById(id);
		return "success";
	}

	@Override
	public String deleteAllUser() {
		repository.deleteAll();
		return "success";
	}
	
}
