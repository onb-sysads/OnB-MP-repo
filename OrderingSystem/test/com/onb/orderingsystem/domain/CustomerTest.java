package com.onb.orderingsystem.domain;

import static org.junit.Assert.*;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

public class CustomerTest {

	@Test
	public void testAddOrder() throws CreditLimitExceededException {
		Customer customer = new Customer(1);
		Order order = new Order(1);
		order.setOrderCustomer(customer);
		order.computeOrderTotalPrice();
		customer.addOrder(order);

		assertFalse(customer.getCustomerOrderList().isEmpty());
	}
	
	@Test(expected = CreditLimitExceededException.class)
	public void testAddOrderFail() throws CreditLimitExceededException, InsufficientProductException {
		Customer customer = new Customer(1);
		Product product1 = new Product(1, "Iphone 4", 100, new BigDecimal(
				"5000.00"));
		
		Order unpaidOrder = new Order(1);
		unpaidOrder.setOrderCustomer(customer);
		OrderItem unpaidOrderItem = new OrderItem(unpaidOrder, product1, 2);
		unpaidOrderItem.computeTotalPrice();
		unpaidOrder.addOrderItem(unpaidOrderItem);
		unpaidOrder.computeOrderTotalPrice();
		customer.addOrder(unpaidOrder);
		
		Order order = new Order(2);
		order.setOrderCustomer(customer);
		OrderItem orderItem = new OrderItem(order, product1, 2);
		orderItem.computeTotalPrice();
		order.addOrderItem(orderItem);
		order.computeOrderTotalPrice();
		order.setPaid(false);
		customer.addOrder(order);

	}

	@Test
	public void testComputeTotalPaidOrders()
			throws InsufficientProductException, CreditLimitExceededException {
		Customer customer = new Customer(1);
		Product product1 = new Product(1, "Iphone 4", 100, new BigDecimal(
				"4000.00"));

		Order order = new Order(1);
		order.setOrderCustomer(customer);
		OrderItem orderItem = new OrderItem(order, product1, 2);
		orderItem.computeTotalPrice();
		order.addOrderItem(orderItem);
		order.computeOrderTotalPrice();
		order.setPaid(true);
		customer.addOrder(order);

		BigDecimal expected = product1.getProductPrice().multiply(
				new BigDecimal(orderItem.getOrderItemQuantity()));

		assertEquals(expected, customer.getTotalPaidOrders());
	}

	@Test
	public void testComputeTotalUnpaidOrders()
			throws InsufficientProductException, CreditLimitExceededException {
		Customer customer = new Customer(1);
		Product product1 = new Product(1, "Iphone 4", 100, new BigDecimal(
				"4000.00"));

		Order order = new Order(1);
		order.setOrderCustomer(customer);
		OrderItem orderItem = new OrderItem(order, product1, 2);
		orderItem.computeTotalPrice();
		order.addOrderItem(orderItem);
		order.computeOrderTotalPrice();
		order.setPaid(false);
		customer.addOrder(order);

		BigDecimal expected = product1.getProductPrice().multiply(
				new BigDecimal(orderItem.getOrderItemQuantity()));

		assertEquals(expected, customer.getTotalUnpaidOrders());
	}

	@Test
	public void testGetTotalPurchases() throws InsufficientProductException, CreditLimitExceededException {
		Customer customer = new Customer(1);
		Product product1 = new Product(1, "Iphone 4", 100, new BigDecimal(
				"4000.00"));

		Order order1 = new Order(1);
		order1.setOrderCustomer(customer);
		OrderItem orderItem = new OrderItem(order1, product1, 1);
		orderItem.computeTotalPrice();
		order1.addOrderItem(orderItem);
		order1.computeOrderTotalPrice();
		customer.addOrder(order1);

		
		Order order2 = new Order(2);
		order2.setOrderCustomer(customer);
		OrderItem orderItem2 = new OrderItem(order2, product1, 1);
		orderItem2.computeTotalPrice();
		order2.addOrderItem(orderItem2);
		order2.computeOrderTotalPrice();
		order2.setPaid(true);
		customer.addOrder(order2);
		
		BigDecimal totalPurchases = new BigDecimal("0.00");
		for (Order order : customer.getCustomerOrderList()) {
			totalPurchases = totalPurchases.add(order.getOrderTotalPrice());
		}

		assertEquals(totalPurchases, customer.getTotalPurchases());
	}

	@Test
	public void testComputeRemainingCreditLimit()
			throws InsufficientProductException, CreditLimitExceededException {
		Customer customer = new Customer(1);
		Product product1 = new Product(1, "Iphone 4", 100, new BigDecimal(
				"4000.00"));

		Order order1 = new Order(1);
		order1.setOrderCustomer(customer);
		OrderItem orderItem = new OrderItem(order1, product1, 2);
		orderItem.computeTotalPrice();
		order1.addOrderItem(orderItem);
		order1.computeOrderTotalPrice();
		order1.setPaid(false);
		customer.addOrder(order1);

		BigDecimal expected = new BigDecimal("10000.00").subtract(product1
				.getProductPrice().multiply(
						new BigDecimal(orderItem.getOrderItemQuantity())));

		assertEquals(expected, customer.getRemainingCreditLimit());
	}
	
	@Test
	public void testCheckDiscount() throws InsufficientProductException, CreditLimitExceededException {
		Customer customer = new Customer(1);
		Product product1 = new Product(1, "Iphone 4", 100, new BigDecimal(
				"40000.00"));
		
		Order paidOrder = new Order(1);
		paidOrder.setOrderCustomer(customer);
		OrderItem paidOrderItem = new OrderItem(paidOrder, product1, 100);
		paidOrderItem.computeTotalPrice();
		paidOrder.addOrderItem(paidOrderItem);
		paidOrder.computeOrderTotalPrice();
		paidOrder.setPaid(true);
		
		List<Order> customerOrderList = new ArrayList<Order>();
		customerOrderList.add(paidOrder);
		customer.setCustomerOrderList(customerOrderList);
		
		assertTrue(customer.checkDiscount());
	}
}
