package com.onb.orderingsystem.domain;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import com.onb.orderingsystem.utils.Enumerators.OrderStatus;


public class Customer {
	private int custID;
	private String companyName;
	private BigDecimal custCreditLimit;
	private List<Order> custOrder = new ArrayList<Order>();
	private static final BigDecimal DISCOUNT_REQUIREMENT = new BigDecimal(1000000.00);
	
	public Customer(int custID, String company, List<Order> custOrder) {
		super();
		this.custID = custID;
		this.companyName = company;
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
		return companyName;
	}

	public String getCompanyName() {
		return companyName;
	}

	public void setCompanyName(String companyName) {
		this.companyName = companyName;
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
	/**
	 * @param order
	 * Adds order to the existing orders of the customer
	 */
	
	public void addOrder(Order order) {
		this.custOrder.add(order);
	}
	/**
	 * Computes the total paid orders of the customer from the history
	 *
	 */
	public final BigDecimal computeTotalPaidOrders() {
		BigDecimal totalPaidOrders = new BigDecimal(0.0);
		for(Order order : this.custOrder){
			if(order.getOrderStatus() == OrderStatus.PAID)
				totalPaidOrders = totalPaidOrders.add(order.computeOrderTotalPrice());
		}
		return totalPaidOrders;
	}
	/**
	 * Computes the total unpaid orders of the customer from the history
	 * 
	 */
	public BigDecimal computeTotalUnpaidOrders() {
		BigDecimal totalUnpaidOrders = new BigDecimal(0.0);
		for(Order order : this.custOrder){
			if(order.getOrderStatus() == OrderStatus.UNPAID)
				totalUnpaidOrders = totalUnpaidOrders.add(order.computeOrderTotalPrice());
		}
		return totalUnpaidOrders;
	}
	/**
	 * Computes the credit limit of a customer
	 *
	 */
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
	/**
	 * @throws CreditLimitExceededException
	 * Checks the credit limit of a customer
	 */
	
	public final BigDecimal checkCreditLimit() throws Exception{
		BigDecimal totalUnpaidOrders = computeTotalUnpaidOrders();
		BigDecimal custCreditLimit = computeCreditLimit();
		BigDecimal remainingCreditLimit = computeCreditLimit().subtract(computeTotalUnpaidOrders());
		if(totalUnpaidOrders.compareTo(custCreditLimit) == 1)
			throw new CreditLimitExceededException("Credit limit exceeded, please change the order to be below the credit limit.");
		else return remainingCreditLimit;
	}
	
	/**
	 * Checks if a customer is entitled a discount 
	 *
	 */

	public boolean checkDiscount() {
		int flag = this.computeTotalPaidOrders().compareTo(DISCOUNT_REQUIREMENT);
		if (flag == 1) { return true; } else return false;
	}
	
}