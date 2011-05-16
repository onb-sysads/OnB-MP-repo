package com.onb.orderingsystem.domain;

import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class Order {

	private final int orderID;
	private Customer orderCustomer;

	private Date orderDate;
	private final String DATE_FORMAT = "yyyy-MM-dd";
	private final DateFormat dateFormat = new SimpleDateFormat(DATE_FORMAT);
	private final Calendar calendar = Calendar.getInstance();

	private List<OrderItem> orderItemList = new ArrayList<OrderItem>();
	private BigDecimal orderTotalPrice;
	private boolean isPaid = false;

	private static final BigDecimal DISCOUNT_RATE = new BigDecimal(".10");

	public Order(int orderID) {
		this.orderID = orderID;
	}

	public Order(int orderID, Customer orderCustomer, Date orderDate,
			List<OrderItem> orderItemList, BigDecimal orderTotalPrice,
			boolean isPaid) {
		super();
		this.orderID = orderID;
		this.orderCustomer = orderCustomer;
		this.orderDate = orderDate;
		this.orderItemList = orderItemList;
		this.orderTotalPrice = orderTotalPrice;
		this.isPaid = isPaid;
	}

	public Customer getOrderCustomer() {
		return orderCustomer;
	}

	public void setOrderCustomer(Customer orderCustomer) {
		this.orderCustomer = orderCustomer;
	}

	public Date getOrderDate() {
		return orderDate;
	}

	public void setOrderDate(Date orderDate) {
		this.orderDate = orderDate;
	}

	public List<OrderItem> getOrderItemList() {
		return orderItemList;
	}

	public void setOrderItemList(List<OrderItem> orderItemList) {
		this.orderItemList = orderItemList;
	}

	public BigDecimal getOrderTotalPrice() {
		return orderTotalPrice;
	}

	public boolean isPaid() {
		return isPaid;
	}

	public void setPaid(boolean isPaid) {
		this.isPaid = isPaid;
	}

	public int getOrderID() {
		return orderID;
	}

	/**
	 * Check if the total price of the current order has exceeded the remaining
	 * credit limit of the customer
	 * 
	 */
	public boolean isOrderValid() {
		if (this.orderTotalPrice.compareTo(this.orderCustomer
				.getRemainingCreditLimit()) == 1) {
			return false;
		} else {
			return true;
		}
	}

	/**
	 * Adds an order item to the order list
	 * 
	 * @param orderItem
	 * @throws InsufficientProductException
	 * 
	 */
	public void addOrderItem(OrderItem orderItem)
			throws InsufficientProductException {
		if (orderItem.checkAvailability()) {
			if (this.checkIfInList(orderItem)) {
				this.consolidateOrderItems(orderItem);
			} else {
				this.getOrderItemList().add(orderItem);
			}
			// orderItem.getOrderItemProduct().updateInventory(
			// orderItem.getOrderItemQuantity());
		}
	}

	/**
	 * Computes the total price of the current order
	 * 
	 * @return
	 * 
	 */
	public void computeOrderTotalPrice() {
		BigDecimal total = new BigDecimal("0.00");
		for (OrderItem orderItem : this.orderItemList) {
			total = total.add(orderItem.getOrderItemTotalPrice());
		}
		if (this.orderCustomer.checkDiscount()) {
			total = this.applyDiscount(total);
		}
		this.orderTotalPrice = total;
	}

	/**
	 * Applies discount to the current order's total price
	 * 
	 * @param orderTotalPrice
	 * 
	 */
	private BigDecimal applyDiscount(BigDecimal orderTotalPrice) {
		return orderTotalPrice.subtract((orderTotalPrice
				.multiply(DISCOUNT_RATE)));
	}

	/**
	 * Consolidates similar order items into single order item
	 * 
	 * @param orderItem
	 * @throws InsufficientProductException
	 * 
	 */
	private void consolidateOrderItems(OrderItem orderItem)
			throws InsufficientProductException {
		int newOrderItemQty = 0;
		List<OrderItem> newOrderItemList = new ArrayList<OrderItem>();
		for (OrderItem lineItem : this.getOrderItemList()) {
			if (lineItem.getOrderItemProduct().equals(
					orderItem.getOrderItemProduct())) {
				newOrderItemQty = lineItem.getOrderItemQuantity()
						+ orderItem.getOrderItemQuantity();
				if (newOrderItemQty > orderItem.getOrderItemProduct()
						.getProductQuantity()) {
					throw new InsufficientProductException(
							"Quantity exceeded product supply inventory!");
				}
				lineItem.setOrderItemQuantity(newOrderItemQty);
			}
			newOrderItemList.add(lineItem);
		}
		this.setOrderItemList(newOrderItemList);
	}

	/**
	 * Checks if an item is similar to the other items in the order list
	 * 
	 */
	private boolean checkIfInList(OrderItem orderItem) {
		for (OrderItem lineItem : this.getOrderItemList()) {
			if (lineItem.getOrderItemProduct().equals(
					orderItem.getOrderItemProduct())) {
				return true;
			}
		}
		return false;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + orderID;
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
		if (orderID != other.orderID)
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Order [DATE_FORMAT=" + DATE_FORMAT + ", calendar=" + calendar
				+ ", dateFormat=" + dateFormat + ", isPaid=" + isPaid
				+ ", orderCustomer=" + orderCustomer + ", orderDate="
				+ orderDate + ", orderID=" + orderID + ", orderItemList="
				+ orderItemList + ", orderTotalPrice=" + orderTotalPrice + "]";
	}
	
}