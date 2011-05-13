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

public class DAOCustomerJdbcImpl implements DAOCustomer{
	private final static String GETALL = "SELECT * FROM CUSTOMER, ORDER, ORDERITEM, PRODUCT";
	private final static String INSERT = "INSERT INTO CUSTOMER (CUSTOMER_ID, CUSTOMER_NAME, CUSTOMER_CREDITLIMIT) "
		+ "VALUES (?,?,?)";
	private final static String UPDATE = "UPDATE CUSTOMER SET CUSTOMER_ID = ?, CUSTOMER_NAME = ?, CUSTOMER_CREDITLIMIT = ?";
	private final static String FINDBYID = "SELECT * FROM CUSTOMER WHERE CUSTOMER_ID = ?";
	
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
		List<Customer> allCust= new ArrayList<Customer>();
		Customer cust = new Customer();
		Connection conn = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		
		try {
			conn = getConnection();
			stmt = conn.prepareStatement(GETALL,
					Statement.RETURN_GENERATED_KEYS);
			rs = stmt.executeQuery(GETALL);
			while (rs.next()){
				if(allCust.contains(cust.getCustID())){
					
				}
				allCust.add(mapRowIntoCustomer(rs));
			}

		} catch (SQLException e) {
			throw new DAOException(e);
		} finally {
			closeResources(rs, stmt, conn);
		}
		
		return allCust;
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
		int custId = rs.getInt("CUSTOMER_ID");
		String custName = rs.getString("CUSTOMER_NAME");
		BigDecimal custCreditLimit = rs.getBigDecimal("CUSTOMER_CREDITLIMIT");
		
		int orderId = rs.getInt("ORDER_ID");
		Date orderDate = rs.getDate("ORDER_DATE");
		BigDecimal orderPrice = rs.getBigDecimal("ORDER_PRICE");
		String orderStatus = rs.getString("ORDER_STATUS");
		int orderCustId = rs.getInt("ORDER_CUSTOMER");
		
		int orderItemQty = rs.getInt("ORDERITEM_QTY");
		int orderItemProduct = rs.getInt("ORDERITEM_PRODUCT");
		int orderItemOrderId = rs.getInt("ORDERITEM_ORDERID");
		
		int productId = rs.getInt("PRODUCT_ID");
		String productName = rs.getString("PRODUCT_NAME");
		int productQty = rs.getInt("PRODUCT_QTY");
		BigDecimal productPrice = rs.getBigDecimal("PRODUCT_PRICE");
		
		if
		
		Product product = new Product(productId, productName, productPrice, productQty);
		List<Order> orderList
		return new Customer(custId, custName, custCreditLimit, new Order(orderId, orderDate, orderPrice, orderStatus, orderCustID, new OrderItem(orderItemId, orderItemQty, orderItemProduct, orderItemOrderID, new Product(productID, productName, productQty, productPrice))));
	}

	@Override
	public void update(Customer c) throws DAOException {
		Connection conn = null;
		PreparedStatement stmt = null;
		try {
			conn = getConnection();
			stmt = conn.prepareStatement(UPDATE_SQL);
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
		Connection conn = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		Customer customer = null;
		try {
			conn = getConnection();
			stmt = conn.prepareStatement(FINDBYID_STMT);
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
	public void create(Customer c) throws DAOException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void delete(Customer c) throws DAOException {
		// TODO Auto-generated method stub
		
	}
}
