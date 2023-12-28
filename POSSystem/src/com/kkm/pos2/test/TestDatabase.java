package com.kkm.pos2.test;

import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import com.kkm.pos2.database.DatabaseConnection;
import com.kkm.pos2.domain.CashPayment;
import com.kkm.pos2.domain.Cashier;
import com.kkm.pos2.domain.Item;
import com.kkm.pos2.domain.Sale;
import com.kkm.pos2.domain.SaleItem;
import com.kkm.pos2.exception.DatabaseConnectionException;
import com.kkm.pos2.exception.RepositoryException;

public class TestDatabase {
	
	
	public static void main(String[] args) throws RepositoryException {
		Sale sale = new Sale("01", new Cashier("01", "dimas"));
		
		try (Connection conn = DatabaseConnection.conn()) {
			PreparedStatement statement = conn.prepareStatement("INSERT INTO sale_test (sale_number, date, cashier) values (?,?,?)");
			statement.setString(1, sale.getSaleNumber());
			statement.setDate(2, new Date(sale.getTransDate().getTime()));;
			statement.setString(3, sale.getCashier().getCashierID());
			statement.executeUpdate();
		}
		catch (SQLException e) {
			throw new RepositoryException(e.getMessage());
		} catch (DatabaseConnectionException e) {
			throw new RepositoryException(e.getMessage());
		}
			}
	
	
	

}
