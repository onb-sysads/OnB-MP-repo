package com.onb.orderingsystem.domain;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import com.onb.orderingsystem.domain.CreditLimitExceededException;

public class Customer {

	private final int customerID;
	private String customerCompanyName = "";
	private List<Order> customerOrderList = new ArrayList<Order>();

	private static final BigDecimal DISCOUNT_REQUIREMENT = new BigDecimal(
			"1000000.00");

	public Customer(int id) {
		super();
		this.customerID = id;
	}

	public Customer(int customerID, String customerCompanyName,
			List<Order> customerOrderList) {
		super();
		this.customerID = customerID;
		this.customerCompanyName = customerCompanyName;
		this.customerOrderList = customerOrderList;
	}

	public String getCustomerCompanyName() {
		return customerCompanyName;
	}

	public void setCustomerCompanyName(String customerCompanyName) {
		this.customerCompanyName = customerCompanyName;
	}

	public List<Order> getCustomerOrderList() {
		return customerOrderList;
	}

	public void setCustomerOrderList(List<Order> customerOrderList) {
		this.customerOrderList = customerOrderList;
	}

	public int getCustomerID() {
		return customerID;
	}

	public BigDecimal getTotalPaidOrders() {
		return this.computeTotalPaidOrders();
	}

	public BigDecimal getTotalUnpaidOrders() {
		return this.computeTotalUnpaidOrders();
	}

	public BigDecimal getTotalPurchases() {
		return this.computeTotalUnpaidOrders().add(computeTotalPaidOrders());
	}

	public BigDecimal getRemainingCreditLimit() {
		return this.computeRemainingCreditLimit();
	}

	/**
	 * @param order
	 *            Adds order to the existing orders of the customer
	 * @throws com.onb.orderingsystem.domain.CreditLimitExceededException
	 * 
	 */
	public void addOrder(Order order) throws CreditLimitExceededException {
		if (order.isOrderValid()) {
			this.customerOrderList.add(order);
			for (OrderItem orderItem : order.getOrderItemList()) {
				orderItem.getOrderItemProduct().updateInventory(
						orderItem.getOrderItemQuantity());
			}
		} else {
			throw new CreditLimitExceededException(
					"The remaining credit limit for that customer has been exceeded!");
		}
	}

	/**
	 * Computes the total paid orders of the customer from the history
	 * 
	 */
	private final BigDecimal computeTotalPaidOrders() {
		BigDecimal totalPaidOrders = new BigDecimal("0.00");
		for (Order order : this.customerOrderList) {
			if (order.isPaid() == true)
				totalPaidOrders = totalPaidOrders.add(order
						.getOrderTotalPrice());
		}
		return totalPaidOrders;
	}

	/**
	 * Computes the total unpaid orders of the customer from the history
	 * 
	 */
	private BigDecimal computeTotalUnpaidOrders() {
		BigDecimal totalUnpaidOrders = new BigDecimal("0.00");
		for (Order order : this.customerOrderList) {
			if (order.isPaid() == false) {
				totalUnpaidOrders = totalUnpaidOrders.add(order
						.getOrderTotalPrice());
			}
		}
		return totalUnpaidOrders;
	}

	/**
	 * Computes the current credit limit of the customer
	 * 
	 */
	private BigDecimal computeRemainingCreditLimit() {
		return this.checkBaseCreditLimit().subtract(
				this.computeTotalUnpaidOrders());

	}

	private BigDecimal checkBaseCreditLimit() {
		BigDecimal totalPaidOrders = this.computeTotalPaidOrders();
		if (totalPaidOrders.compareTo(new BigDecimal("100000.00")) == 0
				|| totalPaidOrders.compareTo(new BigDecimal("100000.00")) == -1) {
			return new BigDecimal("10000.00");
		} else if (totalPaidOrders.compareTo(new BigDecimal("100000.00")) == 1
				&& (totalPaidOrders.compareTo(new BigDecimal("500000.00")) == -1 || totalPaidOrders
						.compareTo(new BigDecimal("500000.00")) == 0)) {
			return new BigDecimal("30000.00");
		} else if (totalPaidOrders.compareTo(new BigDecimal("500000.00")) == 1
				&& (totalPaidOrders.compareTo(new BigDecimal("1000000.00")) == -1 || totalPaidOrders
						.compareTo(new BigDecimal("1000000.00")) == 0)) {
			return new BigDecimal("75000.00");
		} else {
			return new BigDecimal("150000.00");
		}
	}

	/**
	 * Checks if a customer is entitled a discount
	 * 
	 */
	public boolean checkDiscount() {
		int flag = this.computeTotalPaidOrders()
				.compareTo(DISCOUNT_REQUIREMENT);
		if (flag == 1) {
			return true;
		} else
			return false;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + customerID;
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
		Customer other = (Customer) obj;
		if (customerID != other.customerID)
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Customer [customerCompanyName=" + customerCompanyName
				+ ", customerID=" + customerID + ", customerOrderList="
				+ customerOrderList + "]";
	}

}