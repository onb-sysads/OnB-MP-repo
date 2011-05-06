package com.onb.orderingsystem.domain;

import java.util.List;

public class Customer {
	private int custID;
	private String custFirstName;
	private String custLastName;
	private int custCreditLimit;
	private List<Order> custOrder;
	
	public Customer(int custID, String custFirstName, String custLastName,
			int custCreditLimit, List<Order> custOrder) {
		super();
		this.custID = custID;
		this.custFirstName = custFirstName;
		this.custLastName = custLastName;
		this.custCreditLimit = custCreditLimit;
		this.custOrder = custOrder;
	}

	public Customer() {
		super();
	}

	public int getCustID() {
		return custID;
	}

	public void setCustID(int custID) {
		this.custID = custID;
	}

	public String getCustFirstName() {
		return custFirstName;
	}

	public void setCustFirstName(String custFirstName) {
		this.custFirstName = custFirstName;
	}

	public String getCustLastName() {
		return custLastName;
	}

	public void setCustLastName(String custLastName) {
		this.custLastName = custLastName;
	}

	public int getCustCreditLimit() {
		return custCreditLimit;
	}

	public void setCustCreditLimit(int custCreditLimit) {
		this.custCreditLimit = custCreditLimit;
	}

	public List<Order> getCustOrder() {
		return custOrder;
	}

	public void setCustOrder(List<Order> custOrder) {
		this.custOrder = custOrder;
	}
}
