package com.onb.orderingsystem.DAO.impl;

import static org.junit.Assert.*;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.junit.Test;

import com.onb.orderingsystem.DAO.DAOException;
import com.onb.orderingsystem.DAO.DAOOrder;
import com.onb.orderingsystem.DAO.impl.DAOOrderJdbcImpl;
import com.onb.orderingsystem.domain.Customer;
import com.onb.orderingsystem.domain.Order;
import com.onb.orderingsystem.domain.OrderItem;
import com.onb.orderingsystem.domain.Product;

public class DAOOrderJdbcImplTest {

	 private DAOOrder daoOrder = new DAOOrderJdbcImpl();

     @Test
     public void testGetAll() throws DAOException {
     List<Order> initialOrderList = daoOrder.getAll();
	 List<Order> testList = new ArrayList<Order>();
	 
	 List<OrderItem> orderItemList = new ArrayList<OrderItem>();
	 
	 Date date = new java.util.Date(2011-05-18);
	 BigDecimal bd = null;
	 
	 Product product = new Product(8, "MacBook Pro 17", 120, new BigDecimal("107457.00"));
	 Customer customer = new Customer(7, "Ayala Corporation");
	 Order order = new Order(6, customer, date, bd, Boolean.TRUE, orderItemList);
	 OrderItem orderItem = new OrderItem(order, product, 1, bd);
	 orderItemList.add(orderItem);
	 order.setOrderItemList(orderItemList);
	 //assertEquals(new Order(6, customer, date, bd, Boolean.TRUE, orderItemList), daoOrder.getAll());
	 assertTrue(daoOrder.getAll().contains(order));
	 }
     
	@Test
	public void testFindById() throws DAOException {
		List<Order> initialList = daoOrder.getAll();
		List<OrderItem> orderItemList = new ArrayList<OrderItem>();
		
		Product prod1 = new Product(1, "iPhone 4", 500, new BigDecimal(
				"43259.00"));
		Product prod2 = new Product(10, "MacBook Air 11 128GB", 300,
				new BigDecimal("51557.00"));
		Product prod3 = new Product(4, "iPod Touch 64G", 360, new BigDecimal(
				"22749.00"));

		Order order1 = new Order(1, new Customer(1), new Date(2011-05-18),
				BigDecimal.ZERO, Boolean.TRUE, orderItemList);
		
		OrderItem oi1 = new OrderItem(order1, prod1, 4);
		OrderItem oi2 = new OrderItem(order1, prod2, 3);
		OrderItem oi3 = new OrderItem(order1, prod3, 10);
		
		orderItemList.add(oi1);
		orderItemList.add(oi2);
		orderItemList.add(oi3);
		
		order1.setOrderItemList(orderItemList);
		
		initialList.add(order1);

		boolean found = false;
		for(Order order : initialList)  {
			if (order1.getOrderID() == order.getOrderID()) {
				found = true;
				assertEquals(order1.getOrderItemList(), order.getOrderItemList());
			}
		}
		assertTrue(found);
	}
     
 	@Test
 	public void delete() throws DAOException {
 		
 		List<Order> initialList = daoOrder.getAll();
 		List<OrderItem> orderItemList = new ArrayList<OrderItem>();
 		 
 		Date date = new java.util.Date(2011-05-18);
 		BigDecimal bd = null;
 		 
 		Product product = new Product(8, "MacBook Pro 17", 120, new BigDecimal("107457.00"));
 		Customer customer = new Customer(7, "Ayala Corporation");
 		Order order = new Order(6, customer, date, bd, Boolean.TRUE, orderItemList);
 		OrderItem orderItem = new OrderItem(order, product, 1, bd);
 		orderItemList.add(orderItem);
 		order.setOrderItemList(orderItemList);
 		initialList.add(order);
 		
 		daoOrder.delete(order);
 		
 		List<Order> listAfterDeleting = daoOrder.getAll();
 		
 		boolean found = false;
 		for(Order o: listAfterDeleting)  {
 			if (order.getOrderID() == o.getOrderID()) {
 				found = true;
 			}
 		}
 		assertFalse(found);
 	}
}
