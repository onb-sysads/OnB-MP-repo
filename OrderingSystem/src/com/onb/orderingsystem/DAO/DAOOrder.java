package com.onb.orderingsystem.DAO;

import java.util.List;

import com.onb.orderingsystem.domain.Order;


public interface DAOOrder{
	
	List<Order> getAll() throws DAOException;
	void create(Order o) throws DAOException;
	void update(Order o) throws DAOException;
	void delete(Order o) throws DAOException;
	Order findById(int id) throws DAOException;
	
}
