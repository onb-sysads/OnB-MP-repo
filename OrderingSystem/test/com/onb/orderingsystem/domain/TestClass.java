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

		@Test (expected = ProductException.class)
	public void testTransWithSupplyConflict() throws ProductException {
		
		int qtyOrdered = 10;
		Product product = new Product(1, "Apple", 2, new BigDecimal(20));
		Order orderBasket = new Order();
		OrderItem orderItem = new OrderItem(1, product, qtyOrdered);
		orderBasket.addOrderItem(orderItem);
		
	}
	
	@Test 
	public void testOrders() throws ProductException {

		Product product = new Product(1, "Apple", 2, new BigDecimal(20));
		OrderItem orderItem = new OrderItem(1, product, 2);
		
		
		Order order = new Order();
		order.setOrderID(1);
		order.addOrderItem(orderItem);
		
		List <OrderItem> mockList = new ArrayList<OrderItem>();
		mockList.add(orderItem);
		
		assertEquals(mockList,order.getOrderList());
	
	}
	
	@Test
	public void testSameOrderItem () throws ProductException {
		
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
		
		
		
		List <OrderItem> list = new ArrayList<OrderItem>();
		list = order.getOrderList(); 
		assertEquals(7,list.get(0).getOrderItemQuantity());
		
		assertEquals(13,list.get(1).getOrderItemQuantity());
	
		assertEquals(52,list.get(2).getOrderItemQuantity());
			
		
	}
	
}
