package com.onb.orderingsystem.DAO.impl;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.onb.orderingsystem.DAO.DAOCustomer;
import com.onb.orderingsystem.DAO.DAOException;
import com.onb.orderingsystem.domain.Customer;
import com.onb.orderingsystem.domain.Order;
import com.onb.orderingsystem.domain.OrderItem;
import com.onb.orderingsystem.domain.Product;

public class DAOCustomerJdbcImpl implements DAOCustomer  {
	
	private static final String GETALLCUSTOMERS ="SELECT * FROM CUSTOMER";
	private static final String GETCUSTOMERBYID = GETALLCUSTOMERS + " WHERE CUSTOMER_ID = ?";
	static {
		try {
			Class.forName("com.mysql.jdbc.Driver");
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}
	
	private Connection getConnection() throws SQLException{
		Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/ORDERINGSYSTEM","root","");
		return con;
	}
	
	

	@Override
	public Customer findById(int id) throws DAOException, SQLException {
		Connection conn = getConnection();
		Customer customer = null;
		
		int customer_id = 0;
		String customer_name = "";
		
		
	    try{
		       
	        PreparedStatement pstmt = conn.prepareStatement(GETCUSTOMERBYID);
	        pstmt.setInt(1, id);
	        ResultSet rs = pstmt.executeQuery();
	        
	        while (rs.next()){
	        	
	        	
	        	customer_id = rs.getInt("CUSTOMER_ID");
	        	customer_name = rs.getString("CUSTOMER_NAME");        	
	        	customer = new Customer(customer_id, customer_name);
	
	        }
        
      }
      catch (SQLException s){
        System.out.println("SQL statement is not executed!"+ " " +s);
      }
  
      return customer;
	
		
	}

	@Override
	public List<Customer> getAllCustomers() throws DAOException, SQLException {
		List<Customer> listOfCustomers = new ArrayList<Customer>();
		Connection conn = getConnection();
		
		int customer_id = 0;
		String customer_name = "";
		
	      try{
		       
		        PreparedStatement pstmt = conn.prepareStatement(GETALLCUSTOMERS);
		        ResultSet rs = pstmt.executeQuery();
		        
		        while (rs.next()){
		        	
		        	
		        	customer_id = rs.getInt("CUSTOMER_ID");
		        	customer_name = rs.getString("CUSTOMER_NAME");
		        	
		        	Customer customer = new Customer(customer_id, customer_name);
		        	listOfCustomers.add(customer);
		        }
	        
	      }
	      catch (SQLException s){
	        System.out.println("SQL statement is not executed!"+ " " +s);
	      }
	  
	return listOfCustomers;
		
		
	}


}
