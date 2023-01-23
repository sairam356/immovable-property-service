package com.immovable.investmentplatform.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.immovable.investmentplatform.dto.UserRequest;
import com.immovable.investmentplatform.services.UserService;

@RestController
@RequestMapping("/users")

public class UserController {

	@Autowired
	private UserService service;

	@GetMapping(value = "/products")
	public String getProductName() {
		return "Honey";
	}



	@PostMapping(value = "/login")
	public Map<String, String> loginUser(@RequestParam("username") String username, @RequestParam("password") String password) {
		Map<String, String> map = new HashMap<>();
		boolean result = service.loginUser(username, password);

		if (result) {
			map.put("status", "Success");

		} else {
			map.put("status", "failure");
		}
		return map;
	}

	@PostMapping(value = "/logout")
	public String logoutUser(@RequestParam("username") String username, @RequestParam("password") String password) {

		return "Logged out successfully !!";
	}

	//unsecured
	@PostMapping(value = "/signUp")
	public Map<String, String> createUser(@RequestBody UserRequest request) {
		Map<String, String> map = new HashMap<>();
		if (service.checkUserExists(request.getUsername(), request.getEmail())) {

			return map;
		}

		service.createUser(request);
		map.put("status", "Success");
		return map;
	}

	@PostMapping(value = "/forgotPassword")
	public String forgotPassword(@RequestParam("email") String email) {

		service.resetPassword(email);
		return "Link to Reset Password sent successfully !!";
	}

}
