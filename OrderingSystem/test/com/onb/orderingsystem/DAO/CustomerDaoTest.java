package com.onb.orderingsystem.DAO;
import static org.junit.Assert.*;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.onb.orderingsystem.domain.*;
import org.junit.Test;

import com.onb.orderingsystem.DAO.impl.DAOCustomerJdbcImpl;


public class CustomerDaoTest {
	@Test
	public void getAllCustomersTest() throws DAOException, SQLException {
		DAOCustomerJdbcImpl dao = new DAOCustomerJdbcImpl();
		List<Customer> listOfCustomersFromDatabase = new ArrayList<Customer>();
		List<Customer> mockListOfCustomers = new ArrayList<Customer>();
		
		listOfCustomersFromDatabase = dao.getAllCustomers();
		
		Customer mockCustomer1 = new Customer (1, "Jollibee Food Corps" );
		Customer mockCustomer2 = new Customer (2, "Nestle Philippines Incorporated");
		Customer mockCustomer3 = new Customer (3, "San Miguel Foods Incorporated");
		Customer mockCustomer4 = new Customer (4, "BPI Family Savings Bank");
		Customer mockCustomer5 = new Customer (5, "Dole Philippines Incorporated");
		Customer mockCustomer6 = new Customer (6, "Honda Cars Philippines Incorporated");
		Customer mockCustomer7 = new Customer (7, "Ayala Corporation");
		Customer mockCustomer8 = new Customer (8, "ABS-CBN Broadcasting Corporation");
		Customer mockCustomer9 = new Customer (9, "Avon Cosmetics Incorporated");
		Customer mockCustomer10 = new Customer (10, "Isuzu Philippines Corporation");
		
		mockListOfCustomers.add(mockCustomer1);
		mockListOfCustomers.add(mockCustomer2);
		mockListOfCustomers.add(mockCustomer3);
		mockListOfCustomers.add(mockCustomer4);
		mockListOfCustomers.add(mockCustomer5);
		mockListOfCustomers.add(mockCustomer6);
		mockListOfCustomers.add(mockCustomer7);
		mockListOfCustomers.add(mockCustomer8);
		mockListOfCustomers.add(mockCustomer9);
		mockListOfCustomers.add(mockCustomer10);
		
		
		assertEquals(mockListOfCustomers, listOfCustomersFromDatabase);
		
		
		
		
		
	}
	@Test
	public void getCustomerByIDTest() throws DAOException, SQLException{
		DAOCustomerJdbcImpl dao = new DAOCustomerJdbcImpl();
		Customer customerFromDatabase = dao.findById(1);
		Customer mockCustomer = new Customer(1, "Jollibee Food Corps");
		assertEquals(mockCustomer,customerFromDatabase);
		
		
	}
}
