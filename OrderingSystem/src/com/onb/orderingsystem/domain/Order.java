package com.onb.orderingsystem.domain;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import com.onb.orderingsystem.utils.Enumerators.OrderStatus;

public class Order {
	private int orderID;
	
	private String orderDate;
	private final String DATE_FORMAT = "yyyy/MM/dd";
	private final SimpleDateFormat dateFormat = new SimpleDateFormat(DATE_FORMAT);
	private final Calendar calendar = Calendar.getInstance();
	
	private List<OrderItem> orderList = new ArrayList<OrderItem>();
	private List<OrderItem> newOrderList = new ArrayList<OrderItem>();
	
	private OrderStatus orderStatus = OrderStatus.UNPAID;
	
	private BigDecimal orderTotalPrice;
	private Customer orderCustomer;
	
	private static final BigDecimal DISCOUNT_RATE = new BigDecimal(.10);
		
	public Order(int orderID, List<OrderItem> orderList, OrderStatus orderStatus, String orderDate) {
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
	/**
	 * Computes the total amount of the current order
	 *
	 */
	public BigDecimal computeOrderTotalPrice() {
		BigDecimal total = new BigDecimal(0.0);
		for(OrderItem orderItem : orderList){
			total = total.add(orderItem.computeTotalPrice());
		}
		this.orderTotalPrice = total;
		return total;
	}
	/**
	 * @param orderItem
	 * @throws ProductException
	 * Adds an order item to the order list
	 */
	public void addOrderItem(OrderItem orderItem) throws ProductException {
		if(orderItem.checkIfAvailable()) {
			this.orderList.add(orderItem);
		}
	}

	public Customer getOrderCustomer() {
		return orderCustomer;
	}

	public void setOrderCustomer(Customer orderCustomer) {
		this.orderCustomer = orderCustomer;
	}

	public BigDecimal getOrderTotalPrice() {
		return orderTotalPrice;
	}

	public void setOrderTotalPrice(BigDecimal orderTotalPrice) {
		this.orderTotalPrice = orderTotalPrice;
	}
	
	
	/**
	 * Consolidates similar order items into  single order item
	 *
	 */
	public void consolidateItems() {
		for (OrderItem item : orderList) {
			if (checkIfInList(item))
				this.newOrderList.add(item);
			else
				this.newOrderList.add(item);
		}
		setOrderList(this.newOrderList);
	}
	
	/**
	 * Checks if an item is similar to the other items in the order list
	 *
	 */

	private boolean checkIfInList(OrderItem orderItem) {
		int previousQty;
		int addedQty;
		for (OrderItem item : this.newOrderList) {
			if (new Integer(item.getOrderItemProduct().getProductID())
					.equals(new Integer(orderItem.getOrderItemProduct()
							.getProductID()))) {

				previousQty = item.getOrderItemQuantity();
				addedQty = orderItem.getOrderItemQuantity();
				orderItem.setOrderItemQty(previousQty + addedQty);
				this.newOrderList.remove(item);
				return true;
			}
		}
		return false;
	}
	
	/**
	 * Applies discount to the current order
	 *
	 */
	
	public void applyDiscount() {
		if (this.orderCustomer.checkDiscount()) {
			this.orderTotalPrice = this.orderTotalPrice.subtract((this.orderTotalPrice.multiply(DISCOUNT_RATE)));
		}
	}
	
}