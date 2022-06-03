package com.danh.spring.dao;

import java.util.List;
import javax.transaction.Transactional;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.danh.spring.entity.Customer;





@Repository
public class CustomerDAOImpl implements CustomerDAO {

	@Autowired
	private SessionFactory sessionFactory;


	@Transactional
    @Override
	public List<Customer> get() {
		// get the current hibernate session
		Session currentSession = sessionFactory.getCurrentSession();

		// current session query ...sort by last name
		Query<Customer> query = currentSession.createQuery("from Customer order by firstName", Customer.class);
		// execute query and get result list
		List<Customer> customers = query.getResultList();

		// return the results
		return customers;
	}


	@Transactional
	public void save(Customer cus) {
		// get current hibernate session
		Session currentSession = sessionFactory.getCurrentSession();

		// save/ update the customer
		currentSession.saveOrUpdate(cus);
	}


	@Transactional
	public Customer getById(int id) {
		// get current hibernate session
		Session currentSession = sessionFactory.getCurrentSession();

		// read from database using the primary key
		Customer customer = currentSession.get(Customer.class, id);

		return customer;
	}


	@Transactional
	public void delete(int id) {
		// get current hibernate session
		Session currentSession = sessionFactory.getCurrentSession();

		Customer customer = getById(id);

		currentSession.delete(customer);

	}

}
