package com.onb.orderingsystem.domain;

import java.math.BigDecimal;
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

	public BigDecimal computeOrderTotalPrice() {
		BigDecimal totalOrderPrice = new BigDecimal(0.0);
		for(OrderItem orderItem : orderList){
			totalOrderPrice = totalOrderPrice.add(orderItem.computeTotalPrice());
		}
		return totalOrderPrice;
	}
	
		public void addOrderItem(OrderItem orderItem) throws ProductException {
		if(orderItem.checkIfAvailable())
			if(isItemInList(orderItem))
				this.orderList.add(orderItem);
			else this.orderList.add(orderItem);

	}

	private boolean isItemInList(OrderItem orderItem) {
		int previousQty;
		int addedQty;
		for (OrderItem item : orderList) {
			if(new Integer(item.getOrderItemProduct().getProductID()).equals(new Integer(orderItem.getOrderItemProduct().getProductID()))){
				previousQty = item.getOrderItemQuantity();
				addedQty = orderItem.getOrderItemQuantity();
				orderItem.setOrderItemQty(previousQty+addedQty);
				orderList.remove(item);
				return true;
			}
		}
		return false;
	}
	


}
