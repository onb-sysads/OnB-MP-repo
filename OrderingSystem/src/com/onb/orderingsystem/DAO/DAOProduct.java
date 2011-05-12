package com.onb.orderingsystem.DAO;

import java.util.List;

import com.onb.orderingsystem.domain.Product;

public interface DAOProduct {
	List<Product> getAll() throws DAOException;
	void add(Product p) throws DAOException;
	void update(Product p) throws DAOException;
	
}
