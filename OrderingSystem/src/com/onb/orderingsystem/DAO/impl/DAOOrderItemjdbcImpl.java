package com.onb.orderingsystem.DAO.impl;

import java.math.BigDecimal;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import com.onb.orderingsystem.DAO.DAOException;
import com.onb.orderingsystem.DAO.DAOOrderItem;
import com.onb.orderingsystem.domain.*;

public class DAOOrderItemjdbcImpl implements DAOOrderItem{
	
	
	private final static String INSERTANORDERITEM = "INSERT INTO ORDERITEM (ORDERITEM_QTY, ORDERITEM_PRODUCT, ORDERITEM_ORDER, ORDERITEM_PRICE) VALUES (?,?,?,?)";
	private final static String GETALLORDERITEM = "SELECT *  FROM CUSTOMER, ORDERS, PRODUCT, ORDERITEM WHERE ORDERS.ORDER_ID = ORDERITEM.ORDERITEM_ORDERID AND ORDERITEM.ORDERITEM_PRODUCT = PRODUCT.PRODUCT_ID AND CUSTOMER.CUSTOMER_ID = ORDERS.ORDER_CUSTOMER";
	private final static String GETANORDERITEM = GETALLORDERITEM + " AND ORDERITEM.ORDERITEM_ORDERID =? AND ORDERITEM.ORDERITEM_PRODUCT = ?";
	
	
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
	public List<OrderItem> getAll() throws DAOException, SQLException {
		
		List<OrderItem> orderItemList =new ArrayList<OrderItem>();
		OrderItem orderItem;
		
		Connection conn = getConnection();
	
		int orderitem_qty = 0;
		int product_productId;
		BigDecimal orderItemPrice = BigDecimal.ZERO;
		
		
		
		      try{
		       
			        PreparedStatement pstmt = conn.prepareStatement(GETALLORDERITEM);
			        ResultSet rs = pstmt.executeQuery();
			        
			        while (rs.next()){
			        	
			        	orderitem_qty = rs.getInt("ORDERITEM_QTY");
			        	orderItemPrice = rs.getBigDecimal("ORDERITEM_PRICE");
			        	product_productId = rs.getInt("PRODUCT_ID");
			        	Product product = new Product(product_productId);
			        	Order order = new Order(rs.getInt("ORDER_ID"));
			        	orderItem = new OrderItem(product,orderitem_qty ,order);
			        	orderItem.setOrderItemPrice(orderItemPrice);
			        	orderItemList.add(orderItem);
			        }
		        
		      }
		      catch (SQLException s){
		        System.out.println("SQL statement is not executed!"+ " " +s);
		      }
		  
		return orderItemList;
	}

	@Override
	public void update(OrderItem oi) throws DAOException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public  void create(OrderItem orderItem) throws DAOException, SQLException {
		
		//OrderItem orderItem = null;
		Connection conn = getConnection();
		int orderitem_qty = 0;
		int product_id = 0;
		int order_id = 0;
		
		
		      try{
		       
			        PreparedStatement pstmt = conn.prepareStatement(INSERTANORDERITEM);
			        pstmt.setInt(1, orderItem.getOrderItemQuantity());
			        pstmt.setInt(2, orderItem.getOrderItemProduct().getProductID());
			        //TODO prest.setInt(3, orderItem.ge)
			        ResultSet rs = pstmt.executeQuery();
			        
		        
		      }
		      catch (SQLException s){
		        System.out.println("SQL statement is not executed!"+ " " +s);
		      }
		  
		
	}
		
	

	@Override
	public void delete(OrderItem oi) throws DAOException {
		// TODO Auto-generated method stub
		
	}




	@Override
	public OrderItem findOrderItem(int orderId, int productId) throws DAOException, SQLException {
		OrderItem orderItem = null;
		Connection conn = getConnection();
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		int orderitem_qty = 0;
		int product_productId;
		BigDecimal orderitem_price = BigDecimal.ZERO;
	
	      try{
	       
		        pstmt = conn.prepareStatement(GETANORDERITEM);
		        pstmt.setInt(1, orderId);
		        pstmt.setInt(2, productId);
		        rs = pstmt.executeQuery();
		        
		        while (rs.next()){
		        	
		        	
		        	orderitem_qty = rs.getInt("ORDERITEM_QTY");
		        	orderitem_price = rs.getBigDecimal("ORDERITEM_PRICE");
		        	product_productId = rs.getInt("PRODUCT_ID");
		        	Product product = new Product(product_productId);
		        	Order order = new Order(rs.getInt("ORDER_ID"));
		        	System.out.println(orderitem_qty + " " +orderitem_price);
		        	orderItem = new OrderItem(product,orderitem_qty, order );
		        }
	        
	      }
	      catch (SQLException s){
	        System.out.println("SQL statement is not executed!"+ " " +s);
	      }
		  
		return orderItem;
	}

}
