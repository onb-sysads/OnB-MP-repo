package com.onb.orderingsystem.domain;

import static org.junit.Assert.*;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import com.onb.orderingsystem.utils.Enumerators.OrderStatus;

public class TestClass {
	
	@Test 
	public void testOrderItemTotalPrice() {
		Product product = new Product(1, "Apple" , 10, new BigDecimal(20.00));
		OrderItem orderItem = new OrderItem(1, product, 4);
		BigDecimal orderItemTotalPrice = product.getProductPrice().multiply(new BigDecimal(orderItem.getOrderItemQuantity()));
		
		assertEquals(orderItemTotalPrice, orderItem.computeTotalPrice());
	}
	
	@Test
	public void testOrderTotalPrice(){
		Product product = new Product(1, "Apple", 10, new BigDecimal(20.00));
		Product product2 = new Product(2, "Orange", 10, new BigDecimal(25.00));
		OrderItem orderItem = new OrderItem(1, product, 4);
		OrderItem orderItem2 = new OrderItem(2, product2, 5);
		List<OrderItem> orderItemList = new ArrayList<OrderItem>();
		orderItemList.add(orderItem);
		orderItemList.add(orderItem2);
		Order order = new Order(1, orderItemList, OrderStatus.UNPAID);
		
		BigDecimal orderItemTotalPrice1 = orderItem.computeTotalPrice();
		BigDecimal orderItemTotalPrice2 = orderItem2.computeTotalPrice();
		BigDecimal sumOfOrderItemTotalPrices = orderItemTotalPrice1.add(orderItemTotalPrice2);
		
		assertEquals(sumOfOrderItemTotalPrices,order.computeOrderTotalPrice());
	}
	
	@Test
	public void testTotalPaidOrders(){
		Product product = new Product(1, "Apple", 10, new BigDecimal(25.00));
		Product product2 = new Product(2, "Orange", 10, new BigDecimal(25.00));
		OrderItem orderItem = new OrderItem(1, product, 1);
		OrderItem orderItem2 = new OrderItem(2, product2, 1);
		List<OrderItem> orderItemList = new ArrayList<OrderItem>();
		orderItemList.add(orderItem);
		orderItemList.add(orderItem2);
		Order order = new Order(1, orderItemList, OrderStatus.UNPAID);
		Order order2 = new Order(1, orderItemList, OrderStatus.PAID);
		List<Order> orderList = new ArrayList<Order>();
		orderList.add(order);
		orderList.add(order2);
		Customer customer = new Customer(1, "Cyril", "Pangilinan", orderList);
		
		assertEquals(new BigDecimal(50.00),customer.computeTotalPaidOrders());
	}
	
	@Test
	public void testCustomerCreditLimit(){
		Product product = new Product(1, "Apple", 10, new BigDecimal(150000.00));
		Product product2 = new Product(2, "Orange", 10, new BigDecimal(5.00));
		OrderItem orderItem = new OrderItem(1, product, 1);
		OrderItem orderItem2 = new OrderItem(2, product2, 1);
		List<OrderItem> orderItemList = new ArrayList<OrderItem>();
		orderItemList.add(orderItem);
		orderItemList.add(orderItem2);
		Order order = new Order(1, orderItemList, OrderStatus.UNPAID);
		Order order2 = new Order(2, orderItemList, OrderStatus.UNPAID);
		Order order3 = new Order(3, orderItemList, OrderStatus.PAID);
		List<Order> orderList = new ArrayList<Order>();
		orderList.add(order);
		orderList.add(order2);
		orderList.add(order3);
		Customer customer = new Customer(1, "Cyril", "Pangilinan", orderList);
		
		assertEquals(new BigDecimal(150000.00), customer.computeCreditLimit());
	}
}
