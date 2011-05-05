package com.onb.orderingsystem.domain;

public class Product {
	private int prodID;
	private int prodQty;
	private String prodName;
	private int prodUnitPrice;
	
	public Product(int id, int qty, String name, int price){
		this.prodID = id;
		this.prodName = name;
		this.prodQty = qty;
		this.prodUnitPrice = price;
	}
	
	public int getProdID() {
		return prodID;
	}
	public int getProdQty() {
		return prodQty;
	}
	public String getProdName() {
		return prodName;
	}
	public int getProdUnitPrice() {
		return prodUnitPrice;
	}
}
