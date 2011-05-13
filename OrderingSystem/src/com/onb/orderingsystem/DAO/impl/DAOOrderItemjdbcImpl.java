package com.onb.orderingsystem.DAO.impl;

import java.util.List;

import com.onb.orderingsystem.DAO.DAOException;
import com.onb.orderingsystem.DAO.DAOOrderItem;
import com.onb.orderingsystem.domain.OrderItem;

public class DAOOrderItemjdbcImpl implements DAOOrderItem{
	
	
	private final static String INSERTSQL = "INSERT INTO ORDERITEM (ORDERITEM_QTY, ORDERITEM_PRODUCT, ORDERITEM_ORDER, ORDERITEM_PRICE) VALUES (?,?,?,?)";
	private final static String GETALLSQL = "SELECT * FROM ORDERITEM";
	
	
	private final static String JDBCURL = "jdbc:mysql://localhost:3306";
	private final static String JDBCUSER = "root";
	private final static String JDBCPASSWD = "";
	private final static String JDBCDRIVER = "com.mysql.jdbc.Driver";

	@Override
	public OrderItem findById(int id) throws DAOException {

		return null;
	}

	@Override
	public List<OrderItem> getAll() throws DAOException {
		
		return null;
	}

	@Override
	public void update(OrderItem oi) throws DAOException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void create(OrderItem oi) throws DAOException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void delete(OrderItem oi) throws DAOException {
		// TODO Auto-generated method stub
		
	}
	

}
