package com.danh.spring.service;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.danh.spring.dao.CustomerDAO;
import com.danh.spring.entity.Customer;




@Service
public class CustomerSeviceImpl implements CustomerService {

	@Autowired
	private CustomerDAO dao;

	
	@Override
    @Transactional
	public void save(Customer cus) {
		dao.save(cus);
	}

	@Override
    @Transactional
	public Customer getById(int id) {
		return dao.getById(id);
	}

	@Override
    @Transactional
	public void delete(int id) {
		dao.delete(id);
	}

	@Override
    @Transactional
	public List<Customer> get() {
		// TODO Auto-generated method stub
		return dao.get();
	}

}
