package com.onb.orderingsystem.DAO.impl;

import java.util.List;

import com.onb.orderingsystem.DAO.DAOException;
import com.onb.orderingsystem.DAO.DAOOrder;
import com.onb.orderingsystem.domain.Order;

public class DAOOrderJdbcImpl implements DAOOrder{
	private final static String GETALLSQL = "SELECT * FROM ORDERS";
	private final static String INSERTSQL = "INSERT INTO ORDERS (PRODUCT_ID, PRODUCT_NAME, PRODUCT_QTY, PRODUCT_PRICE) VALUES (?,?,?,?)";
	private final static String UPDATESQL = "UPDATE PRODUCT SET PRODUCT_NAME = ?, PRODUCT_QTY = ?, PRODUCT_PRICE = ?"
			+ "WHERE PRODUCT_ID = ?";
	
	private final static String FINDALL_STMT = "SELECT PRODUCT_ID, PRODUCT_NAME, PRODUCT_QTY, PRODUCT_PRICE FROM PRODUCT";
	private final static String FINDBYID_STMT = FINDALL_STMT
			+ " WHERE PRODUCT_ID = ?";
	private final static String DELETEALL_STMT = "DELETE FROM PRODUCT";
	private final static String DELETE_STMT = DELETEALL_STMT
			+ " WHERE PRODUCT_ID = ?";

	private final static String JDBCURL = "jdbc:mysql://localhost:3306/ORDERINGSYSTEM_TEST";
	private final static String JDBCUSER = "root";
	private final static String JDBCPASSWD = "";
	
	@Override
	public List<Order> getAll() throws DAOException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void update(Order o) throws DAOException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Order findById(int id) throws DAOException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void create(Order o) throws DAOException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void delete(Order o) throws DAOException {
		// TODO Auto-generated method stub
		
	}

}
