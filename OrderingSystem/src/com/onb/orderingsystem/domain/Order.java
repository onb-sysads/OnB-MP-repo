package com.onb.orderingsystem.domain;

import java.util.HashSet;
import java.util.Set;

public class Order {
	private int orderID;
	private String orderFCustName;
	private String orderLCustName;
	private Set<OrderItem> orderListOfItems;
	private int orderTotalPrice;
	
	public Order(int id, String fname, String lname, Set<OrderItem> order, int price){
		this.orderID = id;
		this.orderFCustName = fname;
		this.orderLCustName = lname;
		this.orderListOfItems = new HashSet<OrderItem>();
		this.orderTotalPrice = price;
	}
	
	public int getOrderID() {
		return orderID;
	}
	
	public String getOrderFCustName() {
		return orderFCustName;
	}
	
	public String getOrderLCustName() {
		return orderLCustName;
	}

	public Set<OrderItem> getOrderListOfItems() {
		return orderListOfItems;
	}

	public int getOrderTotalPrice() {
		return orderTotalPrice;
	}
	
	public int computeTotalPrice(OrderItem o) {
		int total = 0;
		for(OrderItem oi: this.orderListOfItems) {
			total += oi.getTotal();
		}
		return total;
	}
}
