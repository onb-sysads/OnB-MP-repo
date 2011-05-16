package com.onb.orderingsystem.domain;

import java.math.BigDecimal;

public class Product {

	private final int productID;
	private String productName;
	private int productQuantity; //to do validation if entered quantity = 0
	private BigDecimal productPrice;

	public Product(int productID, String productName, int productQuantity,
			BigDecimal productPrice) {
		super();
		this.productID = productID;
		this.productName = productName;
		this.productQuantity = productQuantity;
		this.productPrice = productPrice;
	}

	public String getProductName() {
		return productName;
	}

	public void setProductName(String productName) {
		this.productName = productName;
	}

	public int getProductQuantity() {
		return productQuantity;
	}

	public void setProductQuantity(int productQuantity) {
		this.productQuantity = productQuantity;
	}

	public BigDecimal getProductPrice() {
		return productPrice;
	}

	public void setProductPrice(BigDecimal productPrice) {
		this.productPrice = productPrice;
	}

	public int getProductID() {
		return productID;
	}

	public void updateInventory(int orderItemQuantity) {
		this.setProductQuantity(this.getProductQuantity() - orderItemQuantity);
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + productID;
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
		Product other = (Product) obj;
		if (productID != other.productID)
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Product [productID=" + productID + ", productName="
				+ productName + ", productPrice=" + productPrice
				+ ", productQuantity=" + productQuantity + "]";
	}

}
