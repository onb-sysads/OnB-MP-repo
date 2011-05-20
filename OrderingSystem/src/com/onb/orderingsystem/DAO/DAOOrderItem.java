package com.onb.orderingsystem.DAO;

import java.util.List;

import com.onb.orderingsystem.domain.OrderItem;

public interface DAOOrderItem{
	
	List<OrderItem> getAll() throws DAOException;
	void update(OrderItem oi) throws DAOException;
	void create(OrderItem oi) throws DAOException;
	void delete(OrderItem oi) throws DAOException;
	OrderItem findOrderItem(int productId) throws DAOException;
	
	
	
}
