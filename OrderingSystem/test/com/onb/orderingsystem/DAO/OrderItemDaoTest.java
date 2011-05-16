package com.onb.orderingsystem.DAO;

import static org.junit.Assert.*;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import com.onb.orderingsystem.DAO.impl.DAOOrderItemjdbcImpl;
import com.onb.orderingsystem.domain.*;


public class OrderItemDaoTest {
	
	
	
	@Test
	public void testFindAnOrderItem() throws DAOException, SQLException {
		DAOOrderItemjdbcImpl dao = new DAOOrderItemjdbcImpl();
		Product mockProduct = new Product(1);
		
		Order order = new Order(1);
		OrderItem oi = new OrderItem(mockProduct,4, order);
		OrderItem oItem = dao.findOrderItem(1, 1);
		
		assertEquals(oItem.toString(),oi.toString());
		
		
	}
	
	public void testGetAllOrderItems() throws DAOException, SQLException{
		DAOOrderItemjdbcImpl dao = new DAOOrderItemjdbcImpl();
		
		List <OrderItem> orderItemListFromDatabase = new ArrayList<OrderItem>();
		List <OrderItem> mockListOfOrderItem = new ArrayList<OrderItem>();

		Order mockOrder1 = new Order(1);
		Order mockOrder2 = new Order(2);
		Order mockOrder3 = new Order(3);
		Order mockOrder4 = new Order(4);
		Order mockOrder5 = new Order(5);
		Order mockOrder6 = new Order(6);
		
		Product mockProduct1 = new Product(1);
		Product mockProduct2 = new Product(2);
		Product mockProduct4 = new Product(4);
		Product mockProduct6 = new Product(6);
		Product mockProduct7 = new Product(7);
		Product mockProduct8 = new Product(8);
		Product mockProduct10 = new Product(10);
		
		
		OrderItem mockOrderItem1 = new OrderItem (mockProduct1, 4, mockOrder1);
		OrderItem mockOrderItem2 = new OrderItem (mockProduct10, 3, mockOrder1);
		OrderItem mockOrderItem3 = new OrderItem (mockProduct4, 10, mockOrder1);
		OrderItem mockOrderItem4 = new OrderItem (mockProduct4, 2, mockOrder2);
		OrderItem mockOrderItem5 = new OrderItem (mockProduct6, 3, mockOrder2);
		OrderItem mockOrderItem6 = new OrderItem (mockProduct1, 100, mockOrder3);
		OrderItem mockOrderItem7 = new OrderItem (mockProduct2, 2, mockOrder3);
		OrderItem mockOrderItem8 = new OrderItem (mockProduct6, 1, mockOrder4);
		OrderItem mockOrderItem9 = new OrderItem (mockProduct7, 1, mockOrder5);
		OrderItem mockOrderItem10 = new OrderItem (mockProduct8, 1, mockOrder6);
		
		
		mockListOfOrderItem.add(mockOrderItem1);
		mockListOfOrderItem.add(mockOrderItem2);
		mockListOfOrderItem.add(mockOrderItem3);
		mockListOfOrderItem.add(mockOrderItem4);
		mockListOfOrderItem.add(mockOrderItem5);
		mockListOfOrderItem.add(mockOrderItem6);
		mockListOfOrderItem.add(mockOrderItem7);
		mockListOfOrderItem.add(mockOrderItem8);
		mockListOfOrderItem.add(mockOrderItem9);
		mockListOfOrderItem.add(mockOrderItem10);
		
		orderItemListFromDatabase = dao.getAll();
				
		assertEquals(mockListOfOrderItem, orderItemListFromDatabase);
		
		
	}
	
	@Test 
	public void createOrderItemTest() throws DAOException, SQLException {
		DAOOrderItemjdbcImpl dao = new DAOOrderItemjdbcImpl();	
		Product mockProduct = new Product(1);
		Order mockOrder = new Order(2);
		OrderItem orderItem = new OrderItem(mockProduct, 7, mockOrder);
		dao.create(orderItem);
		
	}

	@Test 
	public void testDeleteOrderItem() throws DAOException {
		DAOOrderItemjdbcImpl dao = new DAOOrderItemjdbcImpl();
		Product mockProduct = new Product(1);
		Order mockOrder = new Order(2);
		OrderItem orderItem = new OrderItem(mockProduct, 7, mockOrder);
		dao.delete(orderItem);
	}

}
