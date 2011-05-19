package com.onb.orderingsystem.DAO.impl;

import java.math.BigDecimal;
import java.sql.Connection;
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

public class DAOOrderJdbcImpl extends Connections implements DAOOrder {

	private final static String FINDALL_STMT = "SELECT * FROM ORDERS, CUSTOMER, ORDERITEM, PRODUCT"
			+ " WHERE CUSTOMER.CUSTOMER_ID = ORDERS.ORDER_CUSTOMER"
			+ " AND ORDERS.ORDER_ID = ORDERITEM.ORDERITEM_ORDERID"
			+ " AND ORDERITEM.ORDERITEM_PRODUCT = PRODUCT.PRODUCT_ID";
	private final static String FINDBYID_STMT = FINDALL_STMT
			+ " AND ORDER_ID = ?";
	private final static String DELETEALL_STMT = "DELETE FROM ORDERS";
	private final static String DELETE_STMT = DELETEALL_STMT
			+ " WHERE ORDER_ID = ?";

	private OrderItem mapRowOrderItem(ResultSet rs) throws SQLException{
		int prodId = rs.getInt("PRODUCT_ID");
		String prodName = rs.getString("PRODUCT_NAME");
		int prodQty = rs.getInt("PRODUCT_QTY");
		BigDecimal prodPrice = rs.getBigDecimal("PRODUCT_PRICE");
		
		int orderItemQty = rs.getInt("ORDERITEM_QTY");
		int orderItemProdId = rs.getInt("ORDERITEM_PRODUCT");
		int orderId = rs.getInt("ORDER_ID");
		
		Product product = new Product(prodId, prodName, prodQty, prodPrice);
		Order order = new Order(orderId);
		OrderItem orderItem = new OrderItem(order, product, orderItemQty);
		
		return orderItem;
	}
	
	private Order mapRowIntoOrder(ResultSet rs) throws SQLException {
		List<OrderItem> orderItemList = new ArrayList<OrderItem>();
		int orderId = rs.getInt("ORDER_ID");
		Date orderDate = new java.util.Date(rs.getDate("ORDER_DATE").getTime());
		BigDecimal orderPrice = rs.getBigDecimal("ORDER_PRICE");
		boolean orderStatus = Boolean.FALSE ? (rs.getString("ORDER_STATUS")
				.equals("UNPAID")) : Boolean.TRUE;
		int custId = rs.getInt("CUSTOMER_ID");
		
		Customer customer = new Customer(custId);
		
		orderItemList.add(mapRowOrderItem(rs));
		
		Order order = new Order(orderId, customer, orderDate, orderPrice,
				orderStatus, orderItemList);
		
		return order;
	}
	
	@Override
	public List<Order> getAll() throws DAOException {
		List<Order> allOrders = new ArrayList<Order>();
		List<OrderItem> tempList = new ArrayList<OrderItem>();
		Order order = null;
		
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {
			conn = getConnection();
			pstmt = conn.prepareStatement(FINDALL_STMT,
					PreparedStatement.RETURN_GENERATED_KEYS);
			rs = pstmt.executeQuery(FINDALL_STMT);
			while (rs.next()) {
				if(allOrders.contains(mapRowIntoOrder(rs).getOrderID())){
					tempList.add(mapRowOrderItem(rs));
				} else {
					tempList.clear();
					order = mapRowIntoOrder(rs);
					if(allOrders.isEmpty()){
						allOrders.add(order);
					}
					//order = mapRowIntoOrder(rs);
					tempList.add(mapRowOrderItem(rs));
					order.setOrderItemList(tempList);
				}
				
				//order.setOrderItemList(orderItemList);
				//order = mapRowIntoOrder(rs);
				
				allOrders.add(order);
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
		Order order = null;
		List<OrderItem> tempList = new ArrayList<OrderItem>();
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {
			conn = getConnection();
			pstmt = conn.prepareStatement(FINDBYID_STMT,
					PreparedStatement.RETURN_GENERATED_KEYS);
			pstmt.setInt(1, id);
			rs = pstmt.executeQuery();
			tempList.clear();
			if (rs.next()) {
				order = mapRowIntoOrder(rs);
				if(order.getOrderID() == mapRowIntoOrder(rs).getOrderID()){
					tempList.add(mapRowOrderItem(rs));
				}
			}
		
			order.setOrderItemList(tempList);

		} catch (SQLException e) {
			throw new DAOException(e);
		} finally {
			closeResources(rs, pstmt, conn);
		}

		return order;
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
