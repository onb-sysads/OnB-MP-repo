package com.onb.orderingsystem.domain;

import static org.junit.Assert.assertEquals;

import java.math.BigDecimal;

import org.junit.Test;

public class ProductTest {

	@Test
	public void testUpdateInventory() throws InsufficientProductException, CreditLimitExceededException {
		Customer customer = new Customer(1);
		Order order = new Order(1);
		order.setOrderCustomer(customer);
		Product product = new Product(1, "Iphone 4", 10,
				new BigDecimal("4000.00"));
		OrderItem orderItem = new OrderItem(order, product, 1);
		
		// expected new product supply inventory
		int expected = product.getProductQuantity()
				- orderItem.getOrderItemQuantity();
		
		orderItem.computeTotalPrice();
		order.addOrderItem(orderItem);
		order.computeOrderTotalPrice();
		customer.addOrder(order);
					
		int newSupplyInventory = product.getProductQuantity();
	
		assertEquals(expected, newSupplyInventory);
	}
}
