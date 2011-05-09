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

	public BigDecimal computeTotalPrice() {
		BigDecimal bd = new BigDecimal(this.getOrderItemQuantity());
		return this.orderItemProduct.getProductPrice().multiply(bd);
	}
	
	public boolean checkIfAvailable() throws ProductException {
		if(this.orderItemProduct.getProductQuantity() >= this.orderItemQuantity)
		
			return true;
		throw new ProductException("Ordered Qty is greater than the supply ");
	}

	
	
	
}
