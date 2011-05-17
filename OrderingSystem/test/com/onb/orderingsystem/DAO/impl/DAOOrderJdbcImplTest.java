package com.onb.orderingsystem.DAO.impl;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.math.BigDecimal;
import java.util.List;

import org.junit.Test;

import com.onb.orderingsystem.DAO.DAOException;
import com.onb.orderingsystem.DAO.DAOOrder;
import com.onb.orderingsystem.domain.Order;
import com.onb.orderingsystem.domain.OrderItem;
import com.onb.orderingsystem.domain.Product;

public class DAOOrderJdbcImplTest {
	
	private DAOOrder daoOrder = new DAOOrderJdbcImpl();
	
	@Test
	public void testGetAll() throws DAOException{
		List<Order> initialOrderList = daoOrder.getAll();
		List<OrderItem> initialOrderItemList = 
		Product prod = new Product(1, "iPhone 4", 500, new BigDecimal("43259.00"));
		assertEquals(1, prod.getProductID());
		initialList.add(prod);
		List<Product> productAfterAdding = daoProduct.getAll();
		boolean found = false;
		for(Product productAdded : productAfterAdding)  {
			if (productAdded.getProductID() == prod.getProductID()) {
				found = true;
				assertEquals(new Product(1, "iPhone 4", 500, new BigDecimal("43259.00")), prod);
			}
		}
		assertTrue(found);
	}
}
