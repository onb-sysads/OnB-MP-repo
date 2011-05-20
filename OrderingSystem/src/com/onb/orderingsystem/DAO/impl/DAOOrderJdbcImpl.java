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
	
	@Override
	public List<Order> getAll() throws DAOException {
		List<Order> allOrders = new ArrayList<Order>();
		List<OrderItem> orderItemList = new ArrayList<OrderItem>();
		int id = 1;
		
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {
			conn = getConnection();
			pstmt = conn.prepareStatement(FINDALL_STMT,
					PreparedStatement.RETURN_GENERATED_KEYS);
			rs = pstmt.executeQuery(FINDALL_STMT);
			
			while (rs.next()) {
				int orderId = rs.getInt("ORDER_ID");
				Date orderDate = new java.util.Date(rs.getDate("ORDER_DATE").getTime());
				BigDecimal orderPrice = rs.getBigDecimal("ORDER_PRICE");
				boolean orderStatus = Boolean.FALSE ? (rs.getString("ORDER_STATUS")
						.equals("UNPAID")) : Boolean.TRUE;
				int orderCustId = rs.getInt("ORDER_CUSTOMER");
				
				int custId = rs.getInt("CUSTOMER_ID");
				String custName = rs.getString("CUSTOMER_NAME");
				
				int orderItemQty = rs.getInt("ORDERITEM_QTY");
				int orderItemProdId = rs.getInt("ORDERITEM_PRODUCT");
				int orderItemOrderId = rs.getInt("ORDERITEM_ORDERID");
				BigDecimal orderItemPrice = rs.getBigDecimal("ORDERITEM_PRICE");
				
				int productId = rs.getInt("PRODUCT_ID");
				String productName = rs.getString("PRODUCT_NAME");
				int productQty = rs.getInt("PRODUCT_QTY");
				BigDecimal productPrice = rs.getBigDecimal("PRODUCT_PRICE");
				
				Product product = new Product(productId, productName, productQty, productPrice);
				Customer customer = new Customer(custId, custName);
				Order order = new Order(orderId, customer, orderDate, orderPrice, orderStatus, orderItemList);
				OrderItem orderItem = new OrderItem(order, product, orderItemQty, orderItemPrice);
				
				if(orderId == id){
					orderItemList.add(orderItem);
					order.setOrderItemList(orderItemList);
				} else {
					id = orderId;
					orderItemList.clear();
					orderItemList.add(orderItem);
					order.setOrderItemList(orderItemList);
					allOrders.add(order);
				}
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
//			if (rs.next()) {
//				order = mapRowIntoOrder(rs);
//				if(order.getOrderID() == mapRowIntoOrder(rs).getOrderID()){
//					tempList.add(mapRowIntoOrderItem(rs));
//				}
//			}
		
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
		Connection conn = null;
		PreparedStatement pstmt = null;
		
		try {
			conn = getConnection();
			pstmt = conn.prepareStatement(DELETE_STMT);
			pstmt.setInt(1, o.getOrderID());
			pstmt.executeUpdate();
			
		} catch (SQLException e) {
			throw new DAOException(e);
		} finally {
			closeResources(null, pstmt, conn);
		}
	}
}
