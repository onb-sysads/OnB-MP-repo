package com.onb.orderingsystem.DAO;

import java.util.List;

import com.onb.orderingsystem.domain.OrderItem;

public interface DAOOrderItem{
	
	List<OrderItem> getAll() throws DAOException;
	void add(OrderItem oi) throws DAOException;
	void clear() throws DAOException;
	OrderItem findById(int id) throws DAOException;
	
}
