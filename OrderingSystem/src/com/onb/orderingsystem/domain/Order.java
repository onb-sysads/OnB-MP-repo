package com.onb.orderingsystem.domain;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import com.onb.orderingsystem.utils.Enumerators.OrderStatus;

public class Order {
	private final String DATE_FORMAT = "yyyy/MM/dd";
	private final SimpleDateFormat dateFormat = new SimpleDateFormat(
			DATE_FORMAT);
	private final Calendar calendar = Calendar.getInstance();
	private String orderDate;
	private int orderID;
	private List<OrderItem> orderList = new ArrayList<OrderItem>();
	private OrderStatus orderStatus = OrderStatus.UNPAID;

	public Order(int orderID, List<OrderItem> orderList,
			OrderStatus orderStatus, String orderDate) {
		super();
		this.orderID = orderID;
		this.orderList = orderList;
		this.orderStatus = orderStatus;
		this.orderDate = dateFormat.format(calendar.getTime()).toString();
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

	public String getOrderDate() {
		return orderDate;
	}

	public final BigDecimal computeOrderTotalPrice() {
		BigDecimal totalOrderPrice = new BigDecimal(0.0);
		for (OrderItem orderItem : orderList) {
			totalOrderPrice = totalOrderPrice
					.add(orderItem.computeTotalPrice());
		}
		return totalOrderPrice;
	}

	public void addOrderItem(OrderItem orderItem) throws ProductException {
		if (orderItem.checkIfAvailable())
			this.orderList.add(orderItem);
	}

}