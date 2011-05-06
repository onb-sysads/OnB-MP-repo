package com.onb.orderingsystem.domain;

import java.util.List;

public class Order {
	private int orderID;
	private List<OrderItem> orderList;
	
	public Order(int orderID, List<OrderItem> orderList) {
		super();
		this.orderID = orderID;
		this.orderList = orderList;
	}

	public Order() {
		super();
	}

	public int getOrderID() {
		return orderID;
	}

	public void setOrderID(int orderID) {
		this.orderID = orderID;
	}

	public List<OrderItem> getOrderList() {
		return orderList;
	}

	public void setOrderList(List<OrderItem> orderList) {
		this.orderList = orderList;
	}

	public int computeOrderTotalPrice() {
		int totalOrderPrice = 0;
		for(OrderItem orderItem : orderList){
			totalOrderPrice += orderItem.computeTotalPrice();
		}
		return totalOrderPrice;
	}
}
