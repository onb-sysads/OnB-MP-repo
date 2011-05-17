package com.onb.orderingsystem.DAO.impl;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import com.onb.orderingsystem.DAO.DAOCustomer;
import com.onb.orderingsystem.DAO.DAOException;
import com.onb.orderingsystem.domain.Customer;
import com.onb.orderingsystem.domain.Order;
import com.onb.orderingsystem.domain.OrderItem;
import com.onb.orderingsystem.domain.Product;

public class DAOCustomerJdbcImpl implements DAOCustomer{
	
	private final static String GETALLSQL = "SELECT * FROM CUSTOMER, ORDER, ORDERITEM, PRODUCT";
	private final static String INSERTSQL = "INSERT INTO CUSTOMER (CUSTOMER_ID, CUSTOMER_NAME, CUSTOMER_CREDITLIMIT) "
		+ "VALUES (?,?,?)";

	private final static String JDBCURL = "jdbc:mysql://localhost:3306";
	private final static String JDBCUSER = "root";
	private final static String JDBCPASSWD = "";
	private final static String JDBCDRIVER = "com.mysql.jdbc.Driver";
	
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

	private void closeResources(ResultSet rs, PreparedStatement stmt, Connection conn) throws DAOException{
		if (rs != null) {
			try {
				rs.close();
			} catch (SQLException e) {
				throw new DAOException(e);
			}
		}
		if (stmt != null) {
			try {
				stmt.close();
			} catch (SQLException e) {
				throw new DAOException(e);
			}
		}
		if (conn != null) {
			try {
				conn.close();
			} catch (SQLException e) {
				throw new DAOException(e);
			}
		}
	}

	private Customer mapRowIntoCustomer(ResultSet rs) throws SQLException{
		int custId = rs.getInt("CUSTOMER_ID");
		String custName = rs.getString("CUSTOMER_NAME");

		return new Customer(custId, custName);
	}

	@Override
	public void update(Customer c) throws DAOException {
		Connection conn = null;
		PreparedStatement pstmt = null;
		try {
			conn = getConnection();
			pstmt = conn.prepareStatement(UPDATE);
			pstmt.setInt(1, c.getCustID());
			pstmt.setString(2, c.getCompanyName());
			pstmt.setBigDecimal(3, c.getCustCreditLimit());
			pstmt.executeUpdate();

		} catch (SQLException e) {
			throw new DAOException(e);
		} finally {
			closeResources(null, pstmt, conn);
		}
	}

	@Override
	public Customer findById(int id) throws DAOException {
		Connection conn = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		Customer customer = null;
		try {
			conn = getConnection();
			stmt = conn.prepareStatement(FINDBYID);
			stmt.setInt(1, id);
			rs = stmt.executeQuery();
			if (rs.next()) {
				customer = mapRowIntoCustomer(rs);
			}

		} catch (SQLException e) {
			throw new DAOException(e);
		} finally {
			closeResources(rs, stmt, conn);
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



	@Override
	public void create(Customer customer) throws DAOException, SQLException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void delete(Customer customer) throws DAOException, SQLException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void update(Customer customer) throws DAOException, SQLException {
		// TODO Auto-generated method stub
		
	}


}
