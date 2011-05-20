package com.onb.orderingsystem.DAO.impl;

import java.math.BigDecimal;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import com.mysql.jdbc.Driver;
import com.onb.orderingsystem.DAO.DAOException;
import com.onb.orderingsystem.DAO.DAOOrderItem;
import com.onb.orderingsystem.domain.*;

public class DAOOrderItemJdbcImpl extends Connections implements DAOOrderItem {
	private final static String INSERTSQL = "INSERT INTO ORDERITEM (ORDERITEM_QTY, ORDERITEM_PRODUCT, ORDERITEM_ORDERID, ORDERITEM_PRICE) VALUES (?,?,?,?)";
	private final static String UPDATESQL = "UPDATE ORDERITEM SET ORDERITEM_QTY = ?, ORDERITEM_ORDERID = ?, ORDERITEM_PRICE = ?"
			+ " WHERE ORDERITEM_PRODUCT = ?";

	private final static String FINDALL_STMT = "SELECT * FROM ORDERITEM, ORDERS, CUSTOMER, PRODUCT" 
			+ " WHERE ORDERS.ORDER_ID = ORDERITEM.ORDERITEM_ORDERID"
			+ " AND ORDERITEM.ORDERITEM_PRODUCT = PRODUCT.PRODUCT_ID"
			+ " AND CUSTOMER.CUSTOMER_ID = ORDERS.ORDER_CUSTOMER";
	private final static String FINDBYID_STMT = FINDALL_STMT
			+ " AND ORDERITEM_PRODUCT = ? ";
	private final static String DELETE_STMT = "DELETE FROM ORDERITEM"
			+ " WHERE ORDERITEM_PRODUCT = ?";
	
	private Product mapRowIntoProduct(ResultSet rs) throws DAOException {
		int id;
		String prodName;
		int prodQty;
		BigDecimal prodPrice;
		try {
			id = rs.getInt("PRODUCT_ID");
			prodName = rs.getString("PRODUCT_NAME");
			prodQty = rs.getInt("PRODUCT_QTY");
			prodPrice = rs.getBigDecimal("PRODUCT_PRICE");
		} catch (Exception e) {
			throw new DAOException(e);
		}
		return new Product(id, prodName, prodQty, prodPrice);
	}
	
	private Order mapRowIntoOrder(ResultSet rs) throws DAOException{
		int id;
		try {
			id = rs.getInt("ORDER_ID");
		} catch (SQLException e) {
			throw new DAOException(e);
		}
		return new Order(id);
	}
	
	private OrderItem mapRowIntoOrderItem(ResultSet rs) throws DAOException{
		int orderItemQty;
		try {
			orderItemQty = rs.getInt("ORDERITEM_QTY");
		} catch (SQLException e) {
			throw new DAOException(e);
		}
		return new OrderItem(mapRowIntoOrder(rs), mapRowIntoProduct(rs), orderItemQty);
	}
	
	@Override
	public List<OrderItem> getAll() throws DAOException {
		List<OrderItem> orderItemList = new ArrayList<OrderItem>();
		
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {
			conn = getConnection();
			pstmt = conn.prepareStatement(FINDALL_STMT, 
					PreparedStatement.RETURN_GENERATED_KEYS);
			rs = pstmt.executeQuery(FINDALL_STMT);
			while (rs.next()) {
				orderItemList.add(mapRowIntoOrderItem(rs));
			}

		} catch (SQLException e) {
			throw new DAOException(e);
		} finally {
			closeResources(rs, pstmt, conn);
		}

		return orderItemList;
	}

	@Override
	public void update(OrderItem oi) throws DAOException {
		Connection conn = null;
		PreparedStatement pstmt = null;
		
		try{
			conn = getConnection();
			pstmt = conn.prepareStatement(UPDATESQL);
			pstmt.setInt(1, oi.getOrderItemQuantity());
			pstmt.setInt(2, oi.getOrderItemProduct().getProductID());
			pstmt.setInt(3, oi.getOrderItemOrder().getOrderID());
			pstmt.setBigDecimal(4, oi.getOrderItemTotalPrice());
			pstmt.executeUpdate();
			
		} catch (SQLException e){
			throw new DAOException(e);
		} finally {
			closeResources(null, pstmt, conn);
		}
	}

	@Override
	public void create(OrderItem oi) throws DAOException{
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {
			conn = getConnection();
			pstmt = conn.prepareStatement(INSERTSQL,
					Statement.RETURN_GENERATED_KEYS);
			pstmt.setInt(1, oi.getOrderItemQuantity());
			pstmt.setInt(2, oi.getOrderItemProduct().getProductID());
			pstmt.setInt(3, oi.getOrderItemOrder().getOrderID());
			pstmt.setBigDecimal(4, oi.getOrderItemTotalPrice());
			pstmt.executeUpdate();
			rs = pstmt.getGeneratedKeys();
			
		} catch (SQLException e) {
			throw new DAOException(e);
		} finally {
			closeResources(rs, pstmt, conn);
		}

	}

	@Override
	public void delete(OrderItem oi) throws DAOException {
		Connection conn = null;
		PreparedStatement pstmt = null;

		try {
			conn = getConnection();
			pstmt = conn.prepareStatement(DELETE_STMT);
			pstmt.setInt(1, oi.getOrderItemProduct().getProductID());
			pstmt.executeUpdate();
			
		} catch (SQLException e) {
			throw new DAOException(e);
		} finally {
			closeResources(null, pstmt, conn);
		}
	}

	@Override
	public OrderItem findOrderItem(int productId)
			throws DAOException {
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		OrderItem oi = null;

		try {
			conn = getConnection();
			pstmt = conn.prepareStatement(FINDBYID_STMT);
			pstmt.setInt(1, productId);

			rs = pstmt.executeQuery();
			if (rs.next()) {
				oi = mapRowIntoOrderItem(rs);
			}

		} catch (SQLException e) {
			throw new DAOException(e);
		} finally {
			closeResources(rs, pstmt, conn);
		}

		return oi;
	}

}
