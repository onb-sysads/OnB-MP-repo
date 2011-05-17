package com.onb.orderingsystem.DAO.impl;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.onb.orderingsystem.DAO.DAOException;
import com.onb.orderingsystem.DAO.DAOProduct;
import com.onb.orderingsystem.domain.Product;

public class DAOProductJdbcImpl implements DAOProduct {
	private final static String GETALLSQL = "SELECT * FROM PRODUCT";
	private final static String INSERTSQL = "INSERT INTO PRODUCT (PRODUCT_ID, PRODUCT_NAME, PRODUCT_QTY, PRODUCT_PRICE) VALUES (?,?,?,?)";
	private final static String UPDATESQL = "UPDATE PRODUCT SET PRODUCT_NAME = ?, PRODUCT_QTY = ?, PRODUCT_PRICE = ?"
			+ "WHERE PRODUCT_ID = ?";
	
	private final static String FINDALL_STMT = "SELECT PRODUCT_ID, PRODUCT_NAME, PRODUCT_QTY, PRODUCT_PRICE FROM PRODUCT";
	private final static String FINDBYID_STMT = FINDALL_STMT
			+ " WHERE PRODUCT_ID = ?";
	private final static String DELETEALL_STMT = "DELETE FROM PRODUCT";
	private final static String DELETE_STMT = DELETEALL_STMT
			+ " WHERE PRODUCT_ID = ?";

	private final static String JDBCURL = "jdbc:mysql://localhost:3306/ORDERINGSYSTEM_TEST";
	private final static String JDBCUSER = "root";
	private final static String JDBCPASSWD = "";

	private Product mapRowIntoProduct(ResultSet rs) throws SQLException {
		int id = rs.getInt("PRODUCT_ID");
		String prodName= rs.getString("PRODUCT_NAME");
		int prodQty = rs.getInt("PRODUCT_QTY");
		BigDecimal prodPrice = rs.getBigDecimal("PRODUCT_PRICE");
		return new Product(id, prodName, prodQty, prodPrice);
	}

	private void closeResources(ResultSet rs, PreparedStatement pstmt,
			Connection conn) throws DAOException {
		if (rs != null) {
			try {
				rs.close();
			} catch (SQLException e) {
				throw new DAOException(e);
			}
		}
		if (pstmt != null) {
			try {
				pstmt.close();
			} catch (SQLException e) {
				throw new DAOException(e);
			}
		}
		if (conn != null) {
			try {
				conn.close();
			} catch (SQLException e) {
				throw new DAOException(e);
			}
		}

	}

	private Connection getConnection() throws SQLException {
		return DriverManager.getConnection(JDBCURL, JDBCUSER, JDBCPASSWD);
	}
	
	@Override
	public List<Product> getAll() throws DAOException {
		List<Product> all = new ArrayList<Product>();
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {
			conn = getConnection();
			pstmt = conn.prepareStatement(GETALLSQL, 
					PreparedStatement.RETURN_GENERATED_KEYS);
			rs = pstmt.executeQuery(GETALLSQL);
			while (rs.next()) {
				all.add(mapRowIntoProduct(rs));
			}

		} catch (SQLException e) {
			throw new DAOException(e);
		} finally {
			closeResources(rs, pstmt, conn);
		}

		return all;
	}
	
	@Override
	public void update(Product p) throws DAOException {
		Connection conn = null;
		PreparedStatement pstmt = null;
		
		try{
			conn = getConnection();
			pstmt = conn.prepareStatement(UPDATESQL);
			pstmt.setInt(1, p.getProductID());
			pstmt.setString(2, p.getProductName());
			pstmt.setInt(3, p.getProductQuantity());
			pstmt.setBigDecimal(4, p.getProductPrice());
			pstmt.executeUpdate();
			
		} catch (SQLException e){
			throw new DAOException(e);
		} finally {
			closeResources(null, pstmt, conn);
		}
	}

	@Override
	public Product findById(int id) throws DAOException {
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		Product product = null;
		
		try {
			conn = getConnection();
			pstmt = conn.prepareStatement(FINDBYID_STMT);
			pstmt.setInt(1, id);
			rs = pstmt.executeQuery();
			if (rs.next()) {
				product = mapRowIntoProduct(rs);
			}
			
		} catch (SQLException e) {
			throw new DAOException(e);
		} finally {
			closeResources(rs, pstmt, conn);
		}
		return product;
	}

	@Override
	public void create(Product p) throws DAOException {
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		try {
			conn = getConnection();
			pstmt = conn.prepareStatement(INSERTSQL,
					Statement.RETURN_GENERATED_KEYS);
			pstmt.setInt(1, p.getProductID());
			pstmt.setString(2, p.getProductName());
			pstmt.setInt(3, p.getProductQuantity());
			pstmt.setBigDecimal(4, p.getProductPrice());
			pstmt.executeUpdate();
			rs = pstmt.getGeneratedKeys();
//			if (rs.next()) {
//				int id = rs.getInt(1);
//				p.setProductID(id);
//			}

		} catch (SQLException e) {
			throw new DAOException(e);
		} finally {
			closeResources(rs, pstmt, conn);
		}
	}

	@Override
	public void delete(Product p) throws DAOException {
		Connection conn = null;
		PreparedStatement pstmt = null;
		try {
			conn = getConnection();
			pstmt = conn.prepareStatement(DELETE_STMT);
			pstmt.setInt(1, p.getProductID());
			pstmt.executeUpdate();
			
		} catch (SQLException e) {
			throw new DAOException(e);
		} finally {
			closeResources(null, pstmt, conn);
		}
	}
}
