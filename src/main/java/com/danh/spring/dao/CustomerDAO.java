package com.danh.spring.dao;

import java.util.List;

import com.danh.spring.entity.Customer;





public interface CustomerDAO {
	public List<Customer> get();
	public void save(Customer cus);
	public Customer getById(int id);
	public void delete(int id);
}
