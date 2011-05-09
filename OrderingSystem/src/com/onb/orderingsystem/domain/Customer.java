package com.onb.orderingsystem.domain;

import java.math.BigDecimal;
import java.util.List;

import com.onb.orderingsystem.utils.Enumerators.OrderStatus;

public class Customer {
	private int custID;
	private String custFirstName;
	private String custLastName;
	private BigDecimal custCreditLimit;
	private List<Order> custOrder;
	
	public Customer(int custID, String custFirstName, String custLastName, List<Order> custOrder) {
		super();
		this.custID = custID;
		this.custFirstName = custFirstName;
		this.custLastName = custLastName;
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

	public BigDecimal getCustCreditLimit() {
		return custCreditLimit;
	}

	public List<Order> getCustOrder() {
		return custOrder;
	}

	public void setCustOrder(List<Order> custOrder) {
		this.custOrder = custOrder;
	}

	public final BigDecimal computeTotalPaidOrders() {
		BigDecimal totalPaidOrders = new BigDecimal(0.0);
		for(Order order : this.custOrder){
			if(order.getOrderStatus() == OrderStatus.PAID)
				totalPaidOrders = totalPaidOrders.add(order.computeOrderTotalPrice());
		}
		return totalPaidOrders;
	}

	public BigDecimal computeTotalUnpaidOrders() {
		BigDecimal totalUnpaidOrders = new BigDecimal(0.0);
		for(Order order : this.custOrder){
			if(order.getOrderStatus() == OrderStatus.UNPAID)
				totalUnpaidOrders = totalUnpaidOrders.add(order.computeOrderTotalPrice());
		}
		return totalUnpaidOrders;
	}
	
	public final BigDecimal computeCreditLimit() {
		BigDecimal totalPaidOrders = this.computeTotalPaidOrders();
		if(totalPaidOrders.compareTo(new BigDecimal(100000.00)) == -1)
			return this.custCreditLimit = new BigDecimal(10000.00);
		else if((totalPaidOrders.compareTo(new BigDecimal(100000.00)) == 1) && 
					(totalPaidOrders.compareTo(new BigDecimal(500000.00)) == -1))
			return this.custCreditLimit = new BigDecimal(30000.00);
		else if((totalPaidOrders.compareTo(new BigDecimal(500000.00)) == 1) && 
				(totalPaidOrders.compareTo(new BigDecimal(1000000.00)) == -1))
			return this.custCreditLimit = new BigDecimal(75000.00);
		else if((totalPaidOrders.compareTo(new BigDecimal(1000000.00)) == 1))
			return this.custCreditLimit = new BigDecimal(150000.00);
		else return null;
	}

	public final BigDecimal checkCreditLimit() throws Exception{
		BigDecimal totalUnpaidOrders = computeTotalUnpaidOrders();
		BigDecimal custCreditLimit = computeCreditLimit();
		BigDecimal remainingCreditLimit = computeCreditLimit().subtract(computeTotalUnpaidOrders());
		if(totalUnpaidOrders.compareTo(custCreditLimit) == 1)
			throw new CreditLimitExceededException("Credit limit exceeded, please change the order to be below the credit limit.");
		else return remainingCreditLimit;
	}
}