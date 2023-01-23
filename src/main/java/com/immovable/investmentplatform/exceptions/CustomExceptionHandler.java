package com.immovable.investmentplatform.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;


@RestControllerAdvice
public class CustomExceptionHandler extends ResponseEntityExceptionHandler{
	
	@ExceptionHandler(BadCredentialsException.class)
	protected ResponseEntity<String> handleEmployeeNotFoundException(RuntimeException ex, WebRequest request) {
		
		
		return new ResponseEntity<>("Failed to Login", HttpStatus.UNAUTHORIZED);
	}

}
