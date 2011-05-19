package com.onb.orderingsystem.DAO;

import java.sql.SQLException;
import java.util.List;

import com.onb.orderingsystem.domain.OrderItem;

public interface DAOOrderItem{
	
	List<OrderItem> getAll() throws DAOException, SQLException;
	void update(OrderItem oi) throws DAOException;
	void create(OrderItem oi) throws DAOException, SQLException;
	void delete(OrderItem oi) throws DAOException;
	OrderItem findOrderItem(int productId) throws DAOException, SQLException;
	
	
	
}
