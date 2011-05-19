package com.onb.orderingsystem.DAO.impl;

import static org.junit.Assert.*;
import static org.junit.Assert.assertEquals;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.junit.Test;

import com.onb.orderingsystem.DAO.DAOException;
import com.onb.orderingsystem.DAO.DAOOrderItem;
import com.onb.orderingsystem.domain.Customer;
import com.onb.orderingsystem.domain.Order;
import com.onb.orderingsystem.domain.OrderItem;
import com.onb.orderingsystem.domain.Product;

public class DAOOrderItemJdbcImplTest {
	
	private DAOOrderItem daoOrderItem = new DAOOrderItemJdbcImpl();
	
	//Manual adding
//	@Test
//	public void testCreateAndGetAll() throws DAOException, SQLException {
//		List<OrderItem> initialList = daoOrderItem.getAll();
//		List<OrderItem> orderList = new ArrayList<OrderItem>();
//		
//		Product prod = new Product(1, "iPhone 4", 500, new BigDecimal("43259.00"));
//		Product prod2 = new Product(10, "MacBook Air 11 128GB", 300, new BigDecimal("51557.00"));
//		Product prod3 = new Product(4, "iPod Touch 64G", 360, new BigDecimal("22749.50"));
//		
//		//Test OrderItem
//		Product prod4 = new Product(7, "MacBook Pro 15", 30, new BigDecimal("62900.00"));
//		
//		OrderItem order1 = new OrderItem(new Order(1), prod, 4);
//		OrderItem order2 = new OrderItem(new Order(1), prod2, 3);
//		OrderItem order3 = new OrderItem(new Order(1), prod3, 10);
//		
//		orderList.add(order1);
//		orderList.add(order2);
//		orderList.add(order3);
//		
//		Order o = new Order(1, new Customer(1, "Jollibee Food Corps"), new java.util.Date(2011-05-18), BigDecimal.ZERO, Boolean.TRUE, orderList);
//		OrderItem oi = new OrderItem(o, prod4, 4);
//		
//		assertEquals(1, oi.getOrderItemOrder().getOrderID());
//		daoOrderItem.create(oi);
//		List<OrderItem> orderAfterAdding = daoOrderItem.getAll();
//		assertEquals(initialList.size()+1, orderAfterAdding.size());
//		boolean found = false;
//		for(OrderItem o2: orderAfterAdding)  {
//			if (o2.getOrderItemProduct().getProductID() == oi.getOrderItemProduct().getProductID()) {
//				found = true;
//				assertEquals(7, o2.getOrderItemProduct().getProductID());
//			}
//		}
//		assertTrue(found);
//	}
	
	@Test
	public void testFindById() throws DAOException, SQLException{
		List<OrderItem> orderList = new ArrayList<OrderItem>();
		List<OrderItem> initialList = daoOrderItem.getAll();
		Product prod = new Product(1, "iPhone 4", 500, new BigDecimal("43259.00"));
		
		Order o = new Order(1, new Customer(1, "Jollibee Food Corps"), new java.util.Date(2011-05-18), BigDecimal.ZERO, Boolean.TRUE, orderList);
		OrderItem oi = new OrderItem(o, prod, 4);
		
		Order o2 = new Order(3, new Customer(3, "San Miguel Foods Incorporated"), new java.util.Date(2011-05-18), BigDecimal.ZERO, Boolean.FALSE, orderList);
		OrderItem oi2 = new OrderItem(o2, prod, 100);
		
		initialList.add(oi);
		
		assertTrue(initialList.contains(daoOrderItem.findOrderItem(2)));
	}
	
	@Test
	public void testUpdate() throws DAOException, SQLException{
		
	}
	
	@Test
	public void testDelete() throws DAOException, SQLException{
		List<OrderItem> initialList = daoOrderItem.getAll();
		
		Product prod4 = new Product(7, "MacBook Pro 15", 30, new BigDecimal("62900.00"));
		
		Order o = new Order(1, new Customer(1, "Jollibee Food Corps"), new java.util.Date(2011-05-18), BigDecimal.ZERO, Boolean.TRUE, initialList);
		OrderItem oi = new OrderItem(o, prod4, 4);
		
		assertEquals(7, oi.getOrderItemProduct().getProductID());
		
		daoOrderItem.delete(oi);
		List<OrderItem> afterDeletingOrderItem = daoOrderItem.getAll();
		assertEquals(initialList.size() - 1, afterDeletingOrderItem.size());
		
		boolean found = false;
		for(OrderItem oi2: afterDeletingOrderItem)  {
			if (oi2.getOrderItemProduct().getProductID() == oi.getOrderItemProduct().getProductID()) {
				found = true;
			}
		}
		assertFalse(found);
	}
}
