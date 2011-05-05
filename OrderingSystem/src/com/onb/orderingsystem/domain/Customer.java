package com.onb.orderingsystem.domain;

import java.util.HashSet;
import java.util.Set;

public class Customer {
	private int custID;
	private int custCreditLimit;
	private String custFirstName;
	private String custLastName;
	private Set<Order> custOrder;
	
	public Customer(int id, int limit, String fname, String lname,Set<Order> order){
		this.custID = id;
		this.custCreditLimit = limit;
		this.custFirstName = fname;
		this.custLastName = lname;
		this.custOrder = new HashSet<Order>();
	}
	
	public int getCustID() {
		return custID;
	}
	
	public int getCustCreditLimit() {
		return custCreditLimit;
	}

	public String getCustFirstName() {
		return custFirstName;
	}
	
	public String getCustLastName() {
		return custLastName;
	}
	
	public Set<Order> getCustOrder() {
		return custOrder;
	}
}

