package com.onb.orderingsystem.DAO.impl;

import java.math.BigDecimal;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import com.onb.orderingsystem.DAO.DAOCustomer;
import com.onb.orderingsystem.DAO.DAOException;
import com.onb.orderingsystem.domain.Customer;

public class DAOCustomerJdbcImpl implements DAOCustomer{
	private final static String GETALLSQL = "select * from customer";
	private final static String INSERTSQL = "insert into customer (customer_id, customer_name, customer_creditlimit) values (?,?,?)";
	private final static String UPDATESQL = "update customer set customer_id = ?, customer_name = ?, customer_creditlimit = ?";
	private final static String FINDBYID = GETALLSQL + " where customer_id = ?";
	
	private final static String JDBCURL = "jdbc:mysql://localhost:3306";
	private final static String JDBCUSER = "root";
	private final static String JDBCPASSWD = "";
	private final static String JDBCDRIVER = "com.mysql.jdbc.Driver";
	
	static {
		try {
			Class.forName(JDBCDRIVER);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}
	
	private Connection getConnection() throws SQLException {
		return DriverManager.getConnection(JDBCURL, JDBCUSER, JDBCPASSWD);
	}
	
	@Override
	public List<Customer> getAll() throws DAOException {
		List<Customer> allCust = new ArrayList<Customer>();
		Connection conn = null;
		Statement stmt = null;
		ResultSet rs = null;
		
		try {
			conn = getConnection();
			stmt = conn.createStatement();
			rs = stmt.executeQuery(GETALLSQL);
			while (rs.next()) {
				allCust.add(mapRowIntoCustomer(rs));
			}

		} catch (SQLException e) {
			throw new DAOException(e);
		} finally {
			closeResources(rs, stmt, conn);
		}
		
		return null;
	}

	private void closeResources(ResultSet rs, Statement stmt, Connection conn) throws DAOException{
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
		int id = rs.getInt("customer_id");
		String name = rs.getString("customer_name");
		BigDecimal creditLimit = rs.getBigDecimal("customer_creditlimit");
		return new Customer(id, name, creditLimit);
	}

	@Override
	public void update(Customer c) throws DAOException {
		Connection conn = null;
		PreparedStatement stmt = null;
		try {
			conn = getConnection();
			stmt = conn.prepareStatement(UPDATESQL);
			stmt.setInt(1, c.getCustID());
			stmt.setString(2, c.getCompanyName());
			stmt.setBigDecimal(3, c.getCustCreditLimit());
			stmt.executeUpdate();

		} catch (SQLException e) {
			throw new DAOException(e);
		} finally {
			closeResources(null, stmt, conn);
		}
	}

	@Override
	public void clear(Customer c) throws DAOException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Customer findById(int id) throws DAOException {
		// TODO Auto-generated method stub
		return null;
	}
}
