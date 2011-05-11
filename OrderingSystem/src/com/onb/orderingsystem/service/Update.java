package com.onb.orderingsystem.service;
import com.onb.orderingsystem.domain.*;

import java.util.ArrayList;
import java.util.List;

import com.onb.orderingsystem.domain.Order;

public class Update {
	
	private Order order;
	private List<OrderItem> orderList = new ArrayList<OrderItem>();
	private List<OrderItem> newOrderList = new ArrayList<OrderItem>();
	
	public Update(Order order) {
		this.order = order;
		this.orderList = order.getOrderList();
		

	}

	public void consolidateItems() {

	

		for (OrderItem item : orderList) {
			if(checkIfInList(item)) 
				this.newOrderList.add(item);
			else this.newOrderList.add(item);

		}

		order.setOrderList(this.newOrderList);

	}

	private boolean checkIfInList(OrderItem orderItem) {
		int previousQty;
		int addedQty;
		for (OrderItem item : this.newOrderList) {
		
			if(new Integer(item.getOrderItemProduct().getProductID()).equals(new Integer(orderItem.getOrderItemProduct().getProductID()))){
				
				previousQty = item.getOrderItemQuantity();
				addedQty = orderItem.getOrderItemQuantity();
				orderItem.setOrderItemQty(previousQty+addedQty);
				this.newOrderList.remove(item);
				return true;
			}
		}
		return false;
	}

}
