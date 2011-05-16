package com.onb.orderingsystem.domain;

import java.math.BigDecimal;

import com.onb.orderingsystem.domain.InsufficientProductException;

public class OrderItem {

	private final Order orderItemOrder;
	private final Product orderItemProduct;
	private int orderItemQuantity;
	private BigDecimal orderItemTotalPrice;

	public OrderItem(Order orderItemOrder, Product orderItemProduct,
			int orderItemQuantity, BigDecimal orderItemTotalPrice) {
		super();
		this.orderItemOrder = orderItemOrder;
		this.orderItemProduct = orderItemProduct;
		this.orderItemQuantity = orderItemQuantity;
		this.orderItemTotalPrice = orderItemTotalPrice;
	}

	public OrderItem(Order orderItemOrder, Product orderItemProduct,
			int orderItemQuantity) {
		super();
		this.orderItemOrder = orderItemOrder;
		this.orderItemProduct = orderItemProduct;
		this.orderItemQuantity = orderItemQuantity;
	}

	public Order getOrderItemOrder() {
		return orderItemOrder;
	}

	public int getOrderItemQuantity() {
		return orderItemQuantity;
	}

	public void setOrderItemQuantity(int orderItemQuantity) {
		this.orderItemQuantity = orderItemQuantity;
	}

	public Product getOrderItemProduct() {
		return orderItemProduct;
	}

	public BigDecimal getOrderItemTotalPrice() {
		return orderItemTotalPrice;
	}

	/**
	 * Computes the total price of an order item
	 * 
	 */
	public void computeTotalPrice() {
		BigDecimal quantity = new BigDecimal(this.getOrderItemQuantity());
		this.orderItemTotalPrice = this.orderItemProduct.getProductPrice()
				.multiply(quantity);
	}

	/**
	 * Checks the inventory if the supply is sufficient for ordered quantity
	 * 
	 * @throws InsufficientProductException
	 * 
	 */
	public boolean checkAvailability() throws InsufficientProductException {
		if (this.orderItemProduct.getProductQuantity() >= this.orderItemQuantity) {
			return true;
		} else {
			throw new InsufficientProductException(
					"Quantity exceeded product supply inventory!");
		}
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((orderItemOrder == null) ? 0 : orderItemOrder.hashCode());
		result = prime
				* result
				+ ((orderItemProduct == null) ? 0 : orderItemProduct.hashCode());
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
		OrderItem other = (OrderItem) obj;
		if (orderItemOrder == null) {
			if (other.orderItemOrder != null)
				return false;
		} else if (!orderItemOrder.equals(other.orderItemOrder))
			return false;
		if (orderItemProduct == null) {
			if (other.orderItemProduct != null)
				return false;
		} else if (!orderItemProduct.equals(other.orderItemProduct))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "OrderItem [orderItemOrder=" + orderItemOrder
				+ ", orderItemProduct=" + orderItemProduct
				+ ", orderItemQuantity=" + orderItemQuantity
				+ ", orderItemTotalPrice=" + orderItemTotalPrice + "]";
	}

}
