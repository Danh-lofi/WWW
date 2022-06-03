package com.danh.spring.rest;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.danh.spring.entity.Customer;
import com.danh.spring.service.CustomerService;




@RestController
@RequestMapping("/api")
public class CustomerRestController {

	@Autowired
	private CustomerService customerService;
	
	@GetMapping("/customers")
	public List<Customer> getCustomers(){
		return customerService.get();
	}
	
	//find customer by id
		@GetMapping("/customers/{customerId}")
		public Customer getCustomer(@PathVariable int customerId) {
			Customer customer = customerService.getById(customerId);
			if(customer == null) {
				throw new CustomerNotFoundException("Customer id not found - " + customerId);
			}
			
			return customer;
		}
		
		//-- add new customer
		@PostMapping("/customers")
		public Customer addCustomer(@RequestBody Customer customer) {
			customer.setId(0);
			customerService.save(customer);
			return customer;
		}
		
		//-- update
		@PutMapping("/customers")
		public Customer updateCustomer(@RequestBody Customer customer) {
			customerService.save(customer);
			return customer;
		}
		
		//-- delete
		@DeleteMapping("/customers/{customerId}")
		public String deleteCustomer(@PathVariable int customerId) {
			Customer tempCustomer = customerService.getById(customerId);
			if(tempCustomer == null) {
				throw new CustomerNotFoundException("Customer id not found - " + customerId);
			}
			customerService.delete(customerId);
			return "Deleted customer id - " + customerId;
		}
	
//		test mutiple path variables
		@GetMapping("/customers/{customerId}/money/{money}")
		public String getCustomerMoney(@PathVariable int customerId, @PathVariable String money) {
			return "Customer id: " + customerId + " money: " + money;
		}
}

	