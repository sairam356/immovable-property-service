package com.immovable.investmentplatform.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.immovable.investmentplatform.dto.UserResponse;
import com.immovable.investmentplatform.entities.UserEntity;
import com.immovable.investmentplatform.services.AdminService;
import com.immovable.investmentplatform.services.UserService;



@RestController
@RequestMapping("/admin")
@CrossOrigin(origins = "http://localhost:9090")
public class AdminController {

	@Autowired
	private AdminService adminService;
	@Autowired
	private UserService service;

	@GetMapping("/hello")
	public String getProductName() {
		return adminService.sayHello();
	}
	
	@DeleteMapping("/delete/{id}")
	public String deleteUser(@PathVariable String id) {
		
		return adminService.deleteUser(id);
	}
	
	@DeleteMapping("/delete/all")
	public String deleteAllUser() {
		return adminService.deleteAllUser();
	}
	
	@GetMapping(value = "/{userName}")
	public UserResponse getUserByUserName(@PathVariable String userName) {
		return service.findUserByUserName(userName);
	}

	
	@PostMapping
	public UserResponse updateUser(@RequestBody UserEntity user) {
		return service.updateUser(user);
	}
	
	
}
