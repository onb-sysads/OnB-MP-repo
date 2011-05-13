package com.onb.orderingsystem.DAO.impl;

import java.util.List;

import com.onb.orderingsystem.DAO.DAOException;
import com.onb.orderingsystem.DAO.DAOProduct;
import com.onb.orderingsystem.domain.Product;

public class DAOProductImpl implements DAOProduct{
	private final static String GETALLSQL = "SELECT * PRODUCT";
	private final static String INSERTSQL = "INSERT INTO PRODUCT (PRODUCT_ID, PRODUCT_NAME, PRODUCT_QTY, PRODUCT_PRICE) VALUES (?,?,?,?)";
	private final static String UPDATESQL = "UPDATE PRODUCT SET PRODUCT_NAME = ?, PRODUCT_QTY = ?, PRODUCT_PRICE = ?"
			+ "WHERE PRODUCT_ID = ?";
	private final static String FINDALL_STMT = "select person_id, first_name, last_name, birthdate, salary from people";
	private final static String FINDBYID_STMT = FINDALL_STMT
			+ " where person_id = ?";
	private final static String DELETEALL_STMT = "delete from people ";
	private final static String DELETE_STMT = DELETEALL_STMT
			+ " where person_id = ?";

	private final static String JDBCURL = "jdbc:mysql://localhost:3306";
	private final static String JDBCUSER = "root";
	private final static String JDBCPASSWD = "";
	private final static String JDBCDRIVER = "com.mysql.jdbc.Driver";
	
	
	@Override
	public List<Product> getAll() throws DAOException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void update(Product p) throws DAOException {
		// TODO Auto-generated method stub
		
	}
	
	@Override
	public Product findById(int id) throws DAOException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void create(Product p) throws DAOException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void delete(Product p) throws DAOException {
		// TODO Auto-generated method stub
		
	}

}
