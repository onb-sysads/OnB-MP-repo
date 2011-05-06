package com.onb.orderingsystem.domain;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

public class TestClass {
	
	@Test 
	public void testOrderItemTotalPrice() {
		Product product = new Product(1, "Apple" ,10, 20);
		OrderItem orderItem = new OrderItem(1, product, 4);
		
		int orderItemTotalPrice = product.getProductPrice() * orderItem.getOrderItemQuantity();
		assertEquals(orderItemTotalPrice, orderItem.computeTotalPrice());
	}
	
	@Test
	public void testOrderTotalPrice(){
		Product product = new Product(1, "Apple", 10, 20);
		Product product2 = new Product(2, "Orange", 10, 25);
		OrderItem orderItem = new OrderItem(1, product, 4);
		OrderItem orderItem2 = new OrderItem(2, product2, 5);
		List<OrderItem> orderItemList = new ArrayList<OrderItem>();
		orderItemList.add(orderItem);
		orderItemList.add(orderItem2);
		Order order = new Order(1, orderItemList);
		
		int orderItemTotalPrice1 = orderItem.computeTotalPrice();
		int orderItemTotalPrice2 = orderItem2.computeTotalPrice();
		int sumOfOrderItemTotalPrices = orderItemTotalPrice1 + orderItemTotalPrice2;
		
		assertEquals(sumOfOrderItemTotalPrices,order.computeOrderTotalPrice());
	}
}
