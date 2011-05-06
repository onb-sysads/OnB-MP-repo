package com.onb.orderingsystem.domain;

import java.math.BigDecimal;
import java.util.List;

public class Customer {
	private BigDecimal custID;
	private String custFirstName;
	private String custLastName;
	private BigDecimal custCreditLimit;
	private List<Order> custOrder;
	
	public Customer(BigDecimal custID, String custFirstName, String custLastName,
			BigDecimal custCreditLimit, List<Order> custOrder) {
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

	public BigDecimal getCustID() {
		return custID;
	}

	public void setCustID(BigDecimal custID) {
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

	public BigDecimal getCustCreditLimit() {
		return custCreditLimit;
	}

	public void setCustCreditLimit(BigDecimal custCreditLimit) {
		this.custCreditLimit = custCreditLimit;
	}

	public List<Order> getCustOrder() {
		return custOrder;
	}

	public void setCustOrder(List<Order> custOrder) {
		this.custOrder = custOrder;
	}
}
