package com.onb.orderingsystem.DAO;

import java.util.List;

import com.onb.orderingsystem.domain.Customer;

public interface DAOCustomer{
	
	List<Customer> getAll() throws DAOException;
	void create(Customer c) throws DAOException;
	void update(Customer c) throws DAOException;
	void delete(Customer c) throws DAOException;
	Customer findById(int id) throws DAOException;
}
