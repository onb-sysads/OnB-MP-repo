package com.onb.orderingsystem.domain;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.DriverManager;
import java.util.List;

import org.dbunit.DBTestCase;
import org.dbunit.IDatabaseTester;
import org.dbunit.JdbcDatabaseTester;
import org.dbunit.database.DatabaseConnection;
import org.dbunit.database.IDatabaseConnection;
import org.dbunit.dataset.IDataSet;
import org.dbunit.dataset.xml.FlatDtdDataSet;
import org.dbunit.dataset.xml.FlatXmlDataSetBuilder;
import org.dbunit.operation.DatabaseOperation;
import org.junit.Test;

import com.onb.orderingsystem.DAO.DAOException;
import com.onb.orderingsystem.DAO.DAOProduct;
import com.onb.orderingsystem.DAO.impl.DAOProductJdbcImpl;

public class DBUnitDAOProductJdbcImplTest extends DBTestCase{

	private IDatabaseTester databaseTester;
	private DAOProduct daoProduct = new DAOProductJdbcImpl();
	
	@Override
	protected IDataSet getDataSet() throws Exception {
		return new FlatXmlDataSetBuilder().build(new FileInputStream("test.dtd"));
	}
	
//	protected DatabaseOperation getSetUpOperation() throws Exception
//    {
//        return DatabaseOperation.REFRESH;
//    }
//
//    protected DatabaseOperation getTearDownOperation() throws Exception
//    {
//        return DatabaseOperation.NONE;
//    }

    protected void setUp() throws Exception
    {
    	Class driverClass = Class.forName("java.sql.Driver");
        databaseTester = new JdbcDatabaseTester("java.sql.Driver",
            "jdbc:mysql://localhost:3306/ORDERINGSYSTEM", "root", "");

        //initialize your dataset here
        IDataSet dataSet = getDataSet();
        // ...

        databaseTester.setDataSet( dataSet );
	// will call default setUpOperation
        databaseTester.onSetup();
    }
    
    @Test
	public void testGetAll() throws DAOException{
		List<Product> initialList = daoProduct.getAll();
		Product prod = new Product(1, "iPhone 4", 500, new BigDecimal("43259.00"));
		assertEquals(1, prod.getProductID());
		initialList.add(prod);
		List<Product> productAfterAdding = daoProduct.getAll();
		boolean found = false;
		for(Product productAdded : productAfterAdding)  {
			if (productAdded.getProductID() == prod.getProductID()) {
				found = true;
				assertEquals("iPhone 4", productAdded.getProductName());
				assertEquals(500, productAdded.getProductQuantity());
				assertEquals(new BigDecimal("43259.00"), productAdded.getProductPrice());
			}
		}
		assertTrue(found);
	}
    
	public static void main(String[] args) throws Exception
    {
        // database connection
        //Class driverClass = Class.forName("org.hsqldb.jdbcDriver");
        Connection jdbcConnection = DriverManager.getConnection("jdbc:mysql://localhost:3306/ORDERINGSYSTEM", "root", "");
        IDatabaseConnection connection = new DatabaseConnection(jdbcConnection);

        // write DTD file
        FlatDtdDataSet.write(connection.createDataSet(), new FileOutputStream("test.dtd"));
    }
}
