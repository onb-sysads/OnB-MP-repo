package com.onb.orderingsystem.DAO;

import java.util.List;

import com.onb.orderingsystem.domain.Customer;

public interface DAOCustomer{
	List<Customer> getAll() throws DAOException;
	void add(Customer c) throws DAOException;
	void update(Customer c) throws DAOException;
	
}
