package com.onb.orderingsystem.domain;

public class OrderItem {
	private int orderItemID;
	private String orderItemName;
	private int orderItemQty;
	private int orderItemPrice;
	
	private Product product;
	
	public OrderItem(int id, String prod, int qty, int price){
		this.orderItemID = id;
		this.orderItemName = prod;
		this.orderItemQty = qty;
		this.orderItemPrice = price;
	}

	public int getOrderItemID() {
		return orderItemID;
	}

	public String getOrderItemName() {
		return orderItemName;
	}

	public int getOrderItemQty() {
		return orderItemQty;
	}

	public int getOrderItemPrice() {
		return orderItemPrice;
	}

	public void setProduct(Product product) {
		this.product = product;
	}

	public Product getProduct() {
		return product;
	}
	
	public int getTotal() {
		this.orderItemPrice = product.getProdUnitPrice();
		return this.orderItemPrice * this.getOrderItemQty();
	}
}

