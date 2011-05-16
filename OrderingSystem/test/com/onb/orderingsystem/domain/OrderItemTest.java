package com.onb.orderingsystem.domain;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

public class OrderItemTest {

	@Test
	public void testComputeTotalPrice() {
		Order order = new Order(1);
		Product product = new Product(1, "Iphone 4", 10,
				new BigDecimal("20.00"));
		OrderItem orderItem = new OrderItem(order, product, 4);
		orderItem.computeTotalPrice();
		BigDecimal orderItemTotalPrice = product.getProductPrice().multiply(
				new BigDecimal(orderItem.getOrderItemQuantity()));

		assertEquals(orderItemTotalPrice, orderItem.getOrderItemTotalPrice());
	}

	@Test
	public void testCheckAvailability() throws InsufficientProductException {
		Order order = new Order(1);
		Product product = new Product(1, "Iphone 4", 10,
				new BigDecimal("20.00"));
		OrderItem orderItem = new OrderItem(order, product, 10);
		orderItem.checkAvailability();
	}

	@Test(expected = InsufficientProductException.class)
	public void testCheckAvailabilityFail() throws InsufficientProductException {
		Order order = new Order(1);
		Product product = new Product(1, "Iphone 4", 10,
				new BigDecimal("20.00"));
		OrderItem orderItem = new OrderItem(order, product, 11);
		orderItem.checkAvailability();
	}

}
