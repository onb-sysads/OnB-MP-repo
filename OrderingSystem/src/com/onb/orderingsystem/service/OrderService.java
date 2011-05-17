package com.onb.orderingsystem.service;

import com.onb.orderingsystem.DAO.DAOException;
import com.onb.orderingsystem.DAO.impl.DAOOrderImpl;
import com.onb.orderingsystem.domain.*;

public class OrderService {

	

	
	OrderService( ) {	
		
	}
	
	public void update(Order order) {
	
		
		order.computeOrderTotalPrice();
		
		
	}
	public void save(Order order) throws DAOException {
	
		DAOOrderImpl daoOrder = new DAOOrderImpl();
		daoOrder.create(order);
	}
	
	public void cancel(Order order) throws DAOException {
		DAOOrderImpl daoOrder = new DAOOrderImpl();
		daoOrder.delete(order);
	}

	

}
