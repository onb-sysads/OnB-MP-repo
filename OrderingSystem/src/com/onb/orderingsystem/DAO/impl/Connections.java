package com.onb.orderingsystem.DAO.impl;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.onb.orderingsystem.DAO.DAOException;

public class Connections {

	private static final String JDBCURL = "jdbc:mysql://localhost:3306/ORDERINGSYSTEM_TEST";
	private static final String JDBCUSER = "root";
	private static final String JDBCPASSWD = "";

	public Connections() {
		super();
	}

	protected void closeResources(ResultSet rs, PreparedStatement pstmt, Connection conn)
			throws DAOException {
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

	protected Connection getConnection() throws SQLException {
		return DriverManager.getConnection(JDBCURL, JDBCUSER, JDBCPASSWD);
	}

}