package com.onb.orderingsystem.service;

import static org.junit.Assert.assertEquals;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import com.onb.orderingsystem.domain.Order;
import com.onb.orderingsystem.domain.OrderItem;
import com.onb.orderingsystem.domain.Product;
import com.onb.orderingsystem.domain.ProductException;


public class UpdateTest {
	
	@Test
	public void consolidateItemsTest() throws ProductException {
		
		
		Product iPhone = new Product(1, "iPhone", 20, new BigDecimal(30000.50));
		Product iPad = new Product(2, "iPad", 80, new BigDecimal(30000.50));
		Product macAir = new Product(3, "MacAir", 80, new BigDecimal(30000.50));
		
		OrderItem orderItem = new OrderItem(1, iPhone, 2);
		OrderItem orderItem2 = new OrderItem(2, iPhone, 5);
		OrderItem orderItem3 = new OrderItem(3, iPad, 8);
		OrderItem orderItem4 = new OrderItem(4, iPad, 5);
		OrderItem orderItem5 = new OrderItem(5, macAir, 50);
		OrderItem orderItem6 = new OrderItem(6, macAir, 2);
		
		
		Order order = new Order();
		order.setOrderID(1);
		order.addOrderItem(orderItem);
		order.addOrderItem(orderItem2);
		order.addOrderItem(orderItem3);
		order.addOrderItem(orderItem4);
		order.addOrderItem(orderItem5);
		order.addOrderItem(orderItem6);
		
		
	
		
		Update update = new Update(order);
		update.consolidateItems();
		
		
		
		List <OrderItem> list = new ArrayList<OrderItem>();
		list = order.getOrderList(); 
		assertEquals(7,list.get(0).getOrderItemQuantity());	
		assertEquals(13,list.get(1).getOrderItemQuantity());
		assertEquals(52,list.get(2).getOrderItemQuantity());
	

	}

}
