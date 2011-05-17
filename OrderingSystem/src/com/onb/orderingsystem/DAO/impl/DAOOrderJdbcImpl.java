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

import com.onb.orderingsystem.DAO.DAOException;
import com.onb.orderingsystem.DAO.DAOOrder;
import com.onb.orderingsystem.domain.Customer;
import com.onb.orderingsystem.domain.Order;
import com.onb.orderingsystem.domain.OrderItem;
import com.onb.orderingsystem.domain.Product;

public class DAOOrderJdbcImpl implements DAOOrder {
	private final static String GETALLSQL = "SELECT * FROM ORDERS";
	private final static String INSERTSQL = "INSERT INTO ORDERS (ORDER_ID, ORDER_CUSTOMER, ORDER_DATE, ORDER_ORDERITEM, ORDER_PRICE) VALUES (?,?,?,?,?)";
	private final static String UPDATESQL = "UPDATE ORDERS SET ORDER_CUSTOMER = ?, ORDER_DATE = ?, ORDER_ORDERITEM = ?, ORDER_PRICE = ?"
			+ " WHERE ORDER_ID = ?";

	private final static String FINDALL_STMT = "SELECT * FROM ORDERS, CUSTOMER, ORDERITEM, PRODUCT"
			+ " WHERE CUSTOMER.CUSTOMER_ID = ORDERS.ORDER_CUSTOMER"
			+ " AND ORDERS.ORDER_ID = ORDERITEM.ORDERITEM_ORDERID"
			+ " AND ORDERITEM.ORDERITEM_PRODUCT = PRODUCT.PRODUCT_ID";
	private final static String FINDBYID_STMT = FINDALL_STMT
			+ " WHERE ORDER_ID = ?";
	private final static String DELETEALL_STMT = "DELETE FROM ORDERS";
	private final static String DELETE_STMT = DELETEALL_STMT
			+ " WHERE ORDER_ID = ?";

	private final static String JDBCURL = "jdbc:mysql://localhost:3306/ORDERINGSYSTEM_TEST";
	private final static String JDBCUSER = "root";
	private final static String JDBCPASSWD = "";

	// List<Order> allOrders = new ArrayList<Order>();
	
	private OrderItem mapRowFromOrderItem(ResultSet rs) throws SQLException{
		
		int orderItemOrderId = rs.getInt("ORDERITEM_ORDERID");
		int orderItemQty = rs.getInt("ORDERITEM_QTY");
		int orderItemProdId = rs.getInt("ORDERITEM_PRODUCT");
		
		OrderItem orderItem = new OrderItem(mapRowIntoOrder(rs), mapRowFromProduct(rs), orderItemQty);
		
		return orderItem;
	}
	
	private Product mapRowFromProduct(ResultSet rs) throws SQLException{
		
		int prodId = rs.getInt("PRODUCT_ID");
		String prodName = rs.getString("PRODUCT_NAME");
		int prodQty = rs.getInt("PRODUCT_QTY");
		BigDecimal prodPrice = rs.getBigDecimal("PRODUCT_PRICE");
		
		Product product = new Product(prodId, prodName, prodQty, prodPrice);
		
		return product;
	}
	
	private Order mapRowIntoOrder(ResultSet rs) throws SQLException {
		List<OrderItem> orderItemList = new ArrayList<OrderItem>();

		int orderId = rs.getInt("ORDER_ID");
		Date orderDate = rs.getDate("ORDER_DATE");
		BigDecimal orderPrice = rs.getBigDecimal("ORDER_PRICE");
		boolean orderStatus = Boolean.FALSE ? (rs.getString("ORDER_STATUS")
				.equals("UNPAID")) : Boolean.TRUE;
		int orderCust = rs.getInt("ORDER_CUSTOMER");

		int custId = rs.getInt("CUSTOMER_ID");

		Customer customer = new Customer(custId);
		Order order = new Order(orderId, customer, orderDate, orderPrice,
				orderStatus, orderItemList);
		
		orderItemList.add(mapRowFromOrderItem(rs));

		return order;
	}

	private void closeResources(ResultSet rs, PreparedStatement pstmt,
			Connection conn) throws DAOException {
		if (rs != null) {
			try {
				rs.close();
			} catch (SQLException e) {
				throw new DAOException(e);
			}
		}
		if (pstmt != null) {
			try {
				pstmt.close();
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

	private Connection getConnection() throws SQLException {
		return DriverManager.getConnection(JDBCURL, JDBCUSER, JDBCPASSWD);
	}

	@Override
	public List<Order> getAll() throws DAOException {
		List<Order> allOrders = new ArrayList<Order>();
		int previousOrderId = Integer.MAX_VALUE;
		int previousOrderItemId = Integer.MAX_VALUE;
		
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {
			conn = getConnection();
			pstmt = conn.prepareStatement(GETALLSQL,
					PreparedStatement.RETURN_GENERATED_KEYS);
			rs = pstmt.executeQuery(GETALLSQL);
			while (rs.next()) {
				if(allOrders.contains(mapRowIntoOrder(rs).getOrderID())){
					
				}
				allOrders.add(mapRowIntoOrder(rs));
//				if (previousOrderId == rs.getInt("ORDER_ID")) {
//					if(previousOrderItemId == rs.getInt("ORDERITEM_ORDERID")){
//						allOrders.
//					}
//					previousOrderItemId = rs.getInt("ORDERITEM_ORDERID");
//				}
//				previousOrderId = rs.getInt("ORDER_ID");
//				if (getOrderID() == mapRowIntoOrder(rs).getOrderID()) {
//					for (OrderItem o : mapRowIntoOrder(rs)) {
//						for (OrderItem oi : o.getOrderItemList()) {
//
//						}
//					}
//					allOrders.add(mapRowIntoOrder(rs));
//				}
				allOrders.add(mapRowIntoOrder(rs));
			}

		} catch (SQLException e) {
			throw new DAOException(e);
		} finally {
			closeResources(rs, pstmt, conn);
		}

		return allOrders;
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
