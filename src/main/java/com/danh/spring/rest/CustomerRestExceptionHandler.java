package com.danh.spring.rest;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.danh.spring.entity.CustomerErrorRespone;

@ControllerAdvice
public class CustomerRestExceptionHandler {
	// Custom exception if condition fail
	@ExceptionHandler
	public ResponseEntity<CustomerErrorRespone> handleException(CustomerNotFoundException exc) {
		CustomerErrorRespone error = new CustomerErrorRespone();
		error.setStatus(HttpStatus.BAD_REQUEST.value());
		error.setMessage(exc.getMessage());
		error.setTimeStamp(System.currentTimeMillis());
		return new ResponseEntity<CustomerErrorRespone>(error, HttpStatus.BAD_REQUEST);
	}
}
