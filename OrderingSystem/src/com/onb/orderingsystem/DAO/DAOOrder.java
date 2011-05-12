package com.onb.orderingsystem.DAO;

import java.util.List;

import com.onb.orderingsystem.domain.Order;


public interface DAOOrder{
	
	List<Order> getAll() throws DAOException;
	void add(Order o) throws DAOException;
	void update(Order o) throws DAOException;
	void clear() throws DAOException;
	Order findById(int id) throws DAOException;
	
}
