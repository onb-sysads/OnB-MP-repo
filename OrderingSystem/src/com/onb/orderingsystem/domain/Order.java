package com.onb.orderingsystem.domain;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import com.onb.orderingsystem.exceptions.ProductException;
import com.onb.orderingsystem.utils.Enumerators.OrderStatus;

public class Order {
	private int orderID;
	
	private String orderDate;
	private final String DATE_FORMAT = "yyyy/MM/dd";
	private final SimpleDateFormat dateFormat = new SimpleDateFormat(DATE_FORMAT);
	private final Calendar calendar = Calendar.getInstance();
	
	private List<OrderItem> orderItemList = new ArrayList<OrderItem>();
	private List<OrderItem> newOrderItemList = new ArrayList<OrderItem>();
	
	private OrderStatus orderStatus = OrderStatus.UNPAID;
	
	private BigDecimal orderTotalPrice;
	private Customer orderCustomer;
	
	private static final BigDecimal DISCOUNT_RATE = new BigDecimal(.10);
		
	public Order(int orderID, List<OrderItem> orderList, OrderStatus orderStatus, String orderDate) {
		super();
		this.orderID = orderID;
		this.orderItemList = orderList;
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
		return orderItemList;
	}

	public void setOrderList(List<OrderItem> orderList) {
		this.orderItemList = orderList;
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
	
	public void setOrderDate(String orderDate) {
		this.orderDate = orderDate;
	}

	/**
	 * Computes the total amount of the current order
	 *
	 */
	public BigDecimal computeOrderTotalPrice() {
		BigDecimal total = new BigDecimal(0.0);
		for(OrderItem orderItem : orderItemList){
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
			this.orderItemList.add(orderItem);
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
		for (OrderItem item : orderItemList) {
			if (checkIfInList(item))
				this.newOrderItemList.add(item);
			else
				this.newOrderItemList.add(item);
		}
		setOrderList(this.newOrderItemList);
	}
	
	/**
	 * Checks if an item is similar to the other items in the order list
	 *
	 */
	private boolean checkIfInList(OrderItem orderItem) {
		int previousQty;
		int addedQty;
		for (OrderItem item : this.newOrderItemList) {
			if (new Integer(item.getOrderItemProduct().getProductID())
					.equals(new Integer(orderItem.getOrderItemProduct()
							.getProductID()))) {

				previousQty = item.getOrderItemQuantity();
				addedQty = orderItem.getOrderItemQuantity();
				orderItem.setOrderItemQty(previousQty + addedQty);
				this.newOrderItemList.remove(item);
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

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((DATE_FORMAT == null) ? 0 : DATE_FORMAT.hashCode());
		result = prime * result
				+ ((calendar == null) ? 0 : calendar.hashCode());
		result = prime * result
				+ ((dateFormat == null) ? 0 : dateFormat.hashCode());
		result = prime * result
				+ ((newOrderItemList == null) ? 0 : newOrderItemList.hashCode());
		result = prime * result
				+ ((orderCustomer == null) ? 0 : orderCustomer.hashCode());
		result = prime * result
				+ ((orderDate == null) ? 0 : orderDate.hashCode());
		result = prime * result + orderID;
		result = prime * result
				+ ((orderItemList == null) ? 0 : orderItemList.hashCode());
		result = prime * result
				+ ((orderStatus == null) ? 0 : orderStatus.hashCode());
		result = prime * result
				+ ((orderTotalPrice == null) ? 0 : orderTotalPrice.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Order other = (Order) obj;
		if (DATE_FORMAT == null) {
			if (other.DATE_FORMAT != null)
				return false;
		} else if (!DATE_FORMAT.equals(other.DATE_FORMAT))
			return false;
		if (calendar == null) {
			if (other.calendar != null)
				return false;
		} else if (!calendar.equals(other.calendar))
			return false;
		if (dateFormat == null) {
			if (other.dateFormat != null)
				return false;
		} else if (!dateFormat.equals(other.dateFormat))
			return false;
		if (newOrderItemList == null) {
			if (other.newOrderItemList != null)
				return false;
		} else if (!newOrderItemList.equals(other.newOrderItemList))
			return false;
		if (orderCustomer == null) {
			if (other.orderCustomer != null)
				return false;
		} else if (!orderCustomer.equals(other.orderCustomer))
			return false;
		if (orderDate == null) {
			if (other.orderDate != null)
				return false;
		} else if (!orderDate.equals(other.orderDate))
			return false;
		if (orderID != other.orderID)
			return false;
		if (orderItemList == null) {
			if (other.orderItemList != null)
				return false;
		} else if (!orderItemList.equals(other.orderItemList))
			return false;
		if (orderStatus == null) {
			if (other.orderStatus != null)
				return false;
		} else if (!orderStatus.equals(other.orderStatus))
			return false;
		if (orderTotalPrice == null) {
			if (other.orderTotalPrice != null)
				return false;
		} else if (!orderTotalPrice.equals(other.orderTotalPrice))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Order [DATE_FORMAT=" + DATE_FORMAT + ", calendar=" + calendar
				+ ", dateFormat=" + dateFormat + ", newOrderList="
				+ newOrderItemList + ", orderCustomer=" + orderCustomer
				+ ", orderDate=" + orderDate + ", orderID=" + orderID
				+ ", orderList=" + orderItemList + ", orderStatus=" + orderStatus
				+ ", orderTotalPrice=" + orderTotalPrice + "]";
	}
	
}