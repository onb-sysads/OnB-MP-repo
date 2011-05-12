package com.onb.orderingsystem.factory;

import com.onb.orderingsystem.DAO.DAOCustomer;
import com.onb.orderingsystem.DAO.DAOOrder;
import com.onb.orderingsystem.utils.Enumerators;

public class DAOFactory {
	private static DAOFactory daoFactory = new DAOFactory();

    private DAOFactory() {	 }
    
    public static DAOFactory getInstance(){
        return daoFactory;
    }
    
    public static DAOFactory getDaoFactory(String dao){
    	switch (Enumerators.DAO.valueOf(dao)) {
		case CUSTOMER:
			return new DAOCustomer();
		case ORDER:
			return new DAOOrder();
		default
			return new ;
		}
    }
    
}
