package com.onb.orderingsystem.domain;

import java.math.BigDecimal;

public class OrderItem {
	private int orderItemID;
	private Product orderItemProduct;
	private int orderItemQuantity;

	public OrderItem(int orderItemID, Product orderItemProduct, int orderItemQuantity) {
		super();
		this.orderItemID = orderItemID;
		this.orderItemProduct = orderItemProduct;
		this.orderItemQuantity = orderItemQuantity;
	}

	public OrderItem() {
		super();
	}

	public int getOrderItemID() {
		return orderItemID;
	}

	public void setOrderItemID(int orderItemID) {
		this.orderItemID = orderItemID;
	}

	public Product getOrderItemProduct() {
		return orderItemProduct;
	}

	public void setOrderItemProduct(Product orderItemProduct) {
		this.orderItemProduct = orderItemProduct;
	}

	public int getOrderItemQuantity() {
		return orderItemQuantity;
	}

	public void setOrderItemQty(int orderItemQuantty) {
		this.orderItemQuantity = orderItemQuantty;
	}
	
	
	/**
	 * Computes the total amount of an order item
	 *
	 */
	public BigDecimal computeTotalPrice() {
		BigDecimal quantityBD = new BigDecimal(this.getOrderItemQuantity());
		return this.orderItemProduct.getProductPrice().multiply(quantityBD);
	}
	
	
	/**
	 * @throws ProductException
	 * Checks the inventory if the supply is sufficient for ordered quantity
	 *
	 */
	public boolean checkIfAvailable() throws ProductException {
		if (this.orderItemProduct.getProductQuantity() >= this.orderItemQuantity)
			return true;
		throw new ProductException("Ordered Qty exceeded supply inventory");
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + orderItemID;
		result = prime
				* result
				+ ((orderItemProduct == null) ? 0 : orderItemProduct.hashCode());
		result = prime * result + orderItemQuantity;
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
		if (orderItemID != other.orderItemID)
			return false;
		if (orderItemProduct == null) {
			if (other.orderItemProduct != null)
				return false;
		} else if (!orderItemProduct.equals(other.orderItemProduct))
			return false;
		if (orderItemQuantity != other.orderItemQuantity)
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "OrderItem [orderItemID=" + orderItemID + ", orderItemProduct="
				+ orderItemProduct + ", orderItemQuantity=" + orderItemQuantity
				+ "]";
	}

}
