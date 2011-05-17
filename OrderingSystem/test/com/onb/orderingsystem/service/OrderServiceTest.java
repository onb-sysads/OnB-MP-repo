package com.onb.orderingsystem.service;

import static org.junit.Assert.*;

import java.math.BigDecimal;
import java.sql.SQLException;

import org.junit.Test;

import com.onb.orderingsystem.DAO.DAOException;
import com.onb.orderingsystem.DAO.impl.DAOCustomerJdbcImpl;
import com.onb.orderingsystem.DAO.impl.DAOOrderItemjdbcImpl;
import com.onb.orderingsystem.domain.*;

public class OrderServiceTest {
	
	@Test
	public void updateTest() throws DAOException, SQLException, InsufficientProductException, CreditLimitExceededException {
		
		Customer customer = new Customer (1, "Jollibee Food Corps");
		
		Product iphone = new Product(1,"iPhone 4", 500, new BigDecimal("100.00"));
		Product ipad = new Product(2,"iPad 2", 500, new BigDecimal("200.00"));
	
		Order order = new Order(7);
		order.setOrderCustomer(customer);
		
		//DAOOrderItemjdbcImpl orderItemDao = new DAOOrderItemjdbcImpl();
		OrderItem orderItem1 = new OrderItem(order, iphone, 3);
		OrderItem orderItem2 = new OrderItem(order, ipad, 6);
		orderItem1.computeTotalPrice();
		orderItem2.computeTotalPrice();
		
		order.addOrderItem(orderItem1);
		order.addOrderItem(orderItem2);
		
	
		
		OrderService orderService = new OrderService();
		orderService.update(order);
		
		assertEquals(new BigDecimal ("1500.00"),order.getOrderTotalPrice());
		
		
		
		
	}
	
	

}
