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
	
	//not sure if this is required for this class!
	private Customer orderCustomer; //Added
	private BigDecimal orderTotalPrice; //Added
	private static final BigDecimal DISCOUNT_REQUIREMENT = new BigDecimal(1000000.00); //Added
	private static final BigDecimal DISCOUNT_RATE = new BigDecimal(.10); //Added
	
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
	
	public BigDecimal computeOrderTotalPrice() {
		BigDecimal total = new BigDecimal(0.0);
		for(OrderItem orderItem : orderList){
			total = total.add(orderItem.computeTotalPrice());
		}
		this.orderTotalPrice = total;
		return total;
	}
	
	public void addOrderItem(OrderItem orderItem) throws ProductException {
		if(orderItem.checkIfAvailable()) {
			this.orderList.add(orderItem);
		}
	}

	public boolean checkDiscount() {
		int flag = this.orderCustomer.computeTotalPaidOrders().compareTo(Order.DISCOUNT_REQUIREMENT);
		if (flag == 1) { return true; } else return false;
	}
	
	public void applyDiscount() {
		if (this.checkDiscount()) {
			this.orderTotalPrice = this.orderTotalPrice.subtract((this.orderTotalPrice.multiply(Order.DISCOUNT_RATE)));
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

	public void consolidateItems() {
		for (OrderItem item : orderList) {
			if (checkIfInList(item))
				this.newOrderList.add(item);
			else
				this.newOrderList.add(item);
		}
		setOrderList(this.newOrderList);
	}

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
	
}