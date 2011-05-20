package com.onb.orderingsystem.DAO.impl;
import com.onb.orderingsystem.DAO.DAOException;
import com.onb.orderingsystem.DAO.DAOOrder;
import com.onb.orderingsystem.DAO.impl.DAOOrderJdbcImpl;
import com.onb.orderingsystem.domain.Customer;
import com.onb.orderingsystem.domain.Order;
import com.onb.orderingsystem.domain.OrderItem;
import com.onb.orderingsystem.domain.Product;

public class Runner {
	
	private static DAOOrder daoOrder = new DAOOrderJdbcImpl();
	
	public static void main(String[] args) throws DAOException{
		System.out.println(daoOrder.getAll());
	}
}
