package com.onb.orderingsystem.DAO.impl;


import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.math.BigDecimal;
import java.util.List;

import org.junit.Test;

import com.onb.orderingsystem.DAO.DAOException;
import com.onb.orderingsystem.DAO.DAOProduct;
import com.onb.orderingsystem.DAO.impl.DAOProductJdbcImpl;
import com.onb.orderingsystem.domain.Product;

public class DAOProductJdbcImplTest {

	private DAOProduct daoProduct = new DAOProductJdbcImpl();
	
	@Test
	public void testCreateAndGetAll() throws DAOException{
		List<Product> initialList = daoProduct.getAll();
		Product prod = new Product(1, "iPhone 4", 500, new BigDecimal("43259.00"));
		assertEquals(1, prod.getProductID());
		initialList.add(prod);
		List<Product> tempList = daoProduct.getAll();
		boolean found = false;
		for(Product product : tempList)  {
			if (product.getProductID() == prod.getProductID()) {
				found = true;
				assertEquals(prod.getProductName(),product.getProductName());
			}
		}
		assertTrue(found);
	}
	
	@Test
	public void testFindById() throws DAOException{
		List<Product> initialList = daoProduct.getAll();
		Product prod = new Product(1, "iPhone 4", 500, new BigDecimal("43259.00"));
		Product prod2 = new Product(2, "iPad 2", 329, new BigDecimal("35789.00"));
		initialList.add(prod);
		initialList.add(prod2);
		
		assertEquals(prod2, daoProduct.findById(2));
	}
	
	@Test
	public void testCreate() throws DAOException{
		Product prod = new Product(14,"iPhone 4", 500, new BigDecimal("43259.00"));
		daoProduct.create(prod);
		assertEquals(prod, daoProduct.findById(14));
	}

	@Test
	public void testDelete() throws DAOException{
		Product prod = new Product(15,"iPhone 4", 500, new BigDecimal("43259.00"));
		assertEquals(15, prod.getProductID());
		daoProduct.create(prod);
		List<Product> productAfterAdding = daoProduct.getAll();
		daoProduct.delete(prod);
		List<Product> productAfterDeleting = daoProduct.getAll();
		assertEquals(productAfterAdding.size() - 1, productAfterDeleting.size());
		boolean found = false;
		for(Product p: productAfterDeleting)  {
			if (p.getProductID() == prod.getProductID()) {
				found = true;
			}
		}
		assertFalse(found);
	}
}
