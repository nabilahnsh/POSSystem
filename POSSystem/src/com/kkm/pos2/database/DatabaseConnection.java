package com.kkm.pos2.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import com.kkm.pos2.exception.DatabaseConnectionException;

public class DatabaseConnection {
	private Connection conn;
	public static final String userName = "root";
	public static final String password = "";
	public static final String jdbcUrl = "jdbc:mysql://localhost:3306/pos2_db";

	public DatabaseConnection() throws DatabaseConnectionException{
		try {
			this.conn = DriverManager.getConnection(jdbcUrl, userName, password);
		} catch (SQLException e) {
			throw new DatabaseConnectionException(e.getMessage());
		}
		
	}
	
	public Connection getConn() {
		return conn;
	}

	public static Connection conn() throws DatabaseConnectionException {
		return new DatabaseConnection().getConn();
	}

	
	
	
}
