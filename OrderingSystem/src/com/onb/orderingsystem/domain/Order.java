package com.onb.orderingsystem.domain;

import java.math.BigDecimal;
import java.util.List;
import com.onb.orderingsystem.utils.Enumerators.*;

public class Order {
	private int orderID;
	private List<OrderItem> orderList;
	private OrderStatus orderStatus = OrderStatus.UNPAID;
	
	public Order(int orderID, List<OrderItem> orderList, OrderStatus orderStatus) {
		super();
		this.orderID = orderID;
		this.orderList = orderList;
		this.orderStatus = orderStatus;
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
	
	public OrderStatus getOrderStatus() {
		return orderStatus;
	}

	public final void setOrderStatus(OrderStatus orderStatus) {
		this.orderStatus = orderStatus;
	}

	public final BigDecimal computeOrderTotalPrice() {
		BigDecimal totalOrderPrice = new BigDecimal(0.0);
		for(OrderItem orderItem : orderList){
			totalOrderPrice = totalOrderPrice.add(orderItem.computeTotalPrice());
		}
		return totalOrderPrice;
	}
}
