package com.onb.orderingsystem.domain;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.math.BigDecimal;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

public class OrderTest {

	@Test
	public void testIsOrderValid() throws InsufficientProductException {
		Customer customer = new Customer(1);
		Product product = new Product(1, "Iphone 4", 10, new BigDecimal(
				"4000.00"));
		Order order = new Order(1);
		order.setOrderCustomer(customer);
		OrderItem orderItem = new OrderItem(order, product, 2);
		orderItem.computeTotalPrice();
		order.addOrderItem(orderItem);
		order.computeOrderTotalPrice();
		
		assertTrue(order.isOrderValid());
	}

	@Test
	public void testAddOrderItem() throws InsufficientProductException {
		Product product = new Product(1, "Iphone 4", 10, new BigDecimal(
				"40000.00"));
		Order order = new Order(1);
		OrderItem orderItem = new OrderItem(order, product, 9);
		order.addOrderItem(orderItem);
	}

	@Test(expected = InsufficientProductException.class)
	public void testAddOrderItemFail() throws InsufficientProductException {
		Product product = new Product(1, "Iphone 4", 10, new BigDecimal(
				"40000.00"));
		Order order = new Order(1);
		OrderItem orderItem = new OrderItem(order, product, 11);
		order.addOrderItem(orderItem);
	}

	@Test
	public void testComputeOrderTotalPriceWithoutDiscount() {
		Customer customer = new Customer(1);
		Order order = new Order(1);
		order.setOrderCustomer(customer);
		Product product1 = new Product(1, "Iphone 4", 100, new BigDecimal(
				"40000.00"));
		Product product2 = new Product(2, "Ipod Touch 64GB", 50,
				new BigDecimal("18000.00"));
		OrderItem orderItem1 = new OrderItem(order, product1, 4);
		orderItem1.computeTotalPrice();
		OrderItem orderItem2 = new OrderItem(order, product2, 5);
		orderItem2.computeTotalPrice();
		List<OrderItem> orderItemList = new ArrayList<OrderItem>();
		orderItemList.add(orderItem1);
		orderItemList.add(orderItem2);
		order.setOrderItemList(orderItemList);
		order.computeOrderTotalPrice();

		BigDecimal sumOfOrderItemTotalPrices = orderItem1
				.getOrderItemTotalPrice().add(
						orderItem2.getOrderItemTotalPrice());

		assertEquals(sumOfOrderItemTotalPrices, order.getOrderTotalPrice());
	}

	@Test
	public void testComputeOrderTotalPriceWithDiscount() {
		Customer customer = new Customer(1);
		Product product1 = new Product(1, "Iphone 4", 100, new BigDecimal(
				"40000.00"));
		Product product2 = new Product(2, "Ipod Touch 64GB", 50,
				new BigDecimal("18000.00"));

		// customer's previous paid order
		Order paidOrder = new Order(1);
		paidOrder.setOrderCustomer(customer);
		OrderItem paidOrderItem = new OrderItem(paidOrder, product1, 100);
		paidOrderItem.computeTotalPrice();
		List<OrderItem> paidOrderItemList = new ArrayList<OrderItem>();
		paidOrderItemList.add(paidOrderItem);
		paidOrder.setOrderItemList(paidOrderItemList);
		paidOrder.computeOrderTotalPrice();
		paidOrder.setPaid(true);
		List<Order> paidOrderList = new ArrayList<Order>();
		paidOrderList.add(paidOrder);
		customer.setCustomerOrderList(paidOrderList);

		// customer's current order
		Order order = new Order(2);
		order.setOrderCustomer(customer);
		OrderItem orderItem1 = new OrderItem(order, product1, 4);
		orderItem1.computeTotalPrice();
		OrderItem orderItem2 = new OrderItem(order, product2, 5);
		orderItem2.computeTotalPrice();
		List<OrderItem> orderItemList = new ArrayList<OrderItem>();
		orderItemList.add(orderItem1);
		orderItemList.add(orderItem2);
		order.setOrderItemList(orderItemList);
		order.computeOrderTotalPrice();

		BigDecimal sumOfOrderItemTotalPrices = orderItem1
				.getOrderItemTotalPrice().add(
						orderItem2.getOrderItemTotalPrice());
		BigDecimal discountedOrderTotalPrice = sumOfOrderItemTotalPrices
				.subtract(sumOfOrderItemTotalPrices.multiply(new BigDecimal(
						"0.10")));

		assertEquals(discountedOrderTotalPrice, order.getOrderTotalPrice());
	}

	@Test
	public void testAddOrderItemNotInList() throws InsufficientProductException {
		Product product1 = new Product(1, "Iphone 4", 10, new BigDecimal(
				"40000.00"));
		Product product2 = new Product(2, "Ipod Touch 32GB", 50,
				new BigDecimal("15000.00"));
		Product product3 = new Product(3, "Ipod Touch 64GB", 30,
				new BigDecimal("18000.00"));
		Order order = new Order(1);
		OrderItem orderItem1 = new OrderItem(order, product1, 9);
		OrderItem orderItem2 = new OrderItem(order, product2, 23);
		List<OrderItem> orderItemList = new ArrayList<OrderItem>();
		orderItemList.add(orderItem1);
		orderItemList.add(orderItem2);
		order.setOrderItemList(orderItemList);

		OrderItem orderItem3 = new OrderItem(order, product3, 12);
		order.addOrderItem(orderItem3);

		// order item list created without consolidate method
		List<OrderItem> completeOrderItemList = new ArrayList<OrderItem>();
		completeOrderItemList.add(orderItem1);
		completeOrderItemList.add(orderItem2);
		completeOrderItemList.add(orderItem3);

		assertTrue(order.getOrderItemList().equals(completeOrderItemList));
	}

	@Test
	public void testAddOrderItemInList() throws InsufficientProductException {
		Product product1 = new Product(1, "Iphone 4", 10, new BigDecimal(
				"40000.00"));
		Product product2 = new Product(2, "Ipod Touch 32GB", 50,
				new BigDecimal("15000.00"));
		Order order = new Order(1);
		OrderItem orderItem1 = new OrderItem(order, product1, 9);
		OrderItem orderItem2 = new OrderItem(order, product2, 23);
		List<OrderItem> orderItemList = new ArrayList<OrderItem>();
		orderItemList.add(orderItem1);
		orderItemList.add(orderItem2);
		order.setOrderItemList(orderItemList);

		OrderItem orderItem3 = new OrderItem(order, product2, 12);
		order.addOrderItem(orderItem3);

		// order item list created without consolidate method
		List<OrderItem> completeOrderItemList = new ArrayList<OrderItem>();
		completeOrderItemList.add(orderItem1);
		completeOrderItemList.add(orderItem2);
		completeOrderItemList.add(orderItem3);

		assertFalse(order.getOrderItemList().equals(completeOrderItemList));
	}

	@Test
	public void testAddOrderItemWithInventoryCheck()
			throws InsufficientProductException {
		Product product1 = new Product(1, "Iphone 4", 10, new BigDecimal(
				"40000.00"));
		Product product2 = new Product(2, "Ipod Touch 32GB", 50,
				new BigDecimal("15000.00"));
		Order order = new Order(1);
		OrderItem orderItem1 = new OrderItem(order, product1, 9);
		OrderItem orderItem2 = new OrderItem(order, product2, 23);
		order.addOrderItem(orderItem1);
		order.addOrderItem(orderItem2);

		OrderItem orderItem3 = new OrderItem(order, product2, 12);
		order.addOrderItem(orderItem3);

	}

	@Test(expected = InsufficientProductException.class)
	public void testAddOrderItemWithInventoryCheckFail()
			throws InsufficientProductException {
		Product product1 = new Product(1, "Iphone 4", 10, new BigDecimal(
				"40000.00"));
		Product product2 = new Product(2, "Ipod Touch 32GB", 50,
				new BigDecimal("15000.00"));
		Order order = new Order(1);
		OrderItem orderItem1 = new OrderItem(order, product1, 9);
		OrderItem orderItem2 = new OrderItem(order, product2, 23);
		order.addOrderItem(orderItem1);
		order.addOrderItem(orderItem2);

		OrderItem orderItem3 = new OrderItem(order, product2, 28);
		order.addOrderItem(orderItem3);

	}

}
