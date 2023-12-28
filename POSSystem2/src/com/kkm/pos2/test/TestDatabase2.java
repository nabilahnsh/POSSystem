package com.kkm.pos2.test;

import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.kkm.pos2.domain.Cashier;
import com.kkm.pos2.domain.Item;
import com.kkm.pos2.domain.Sale;
import com.kkm.pos2.domain.SaleItem;

public class TestDatabase2 {
	static String userName = "root";
	static String password = "";
	static String jdbcUrl = "jdbc:mysql://localhost:3306/pos2_db";
	
	
	public static void main(String[] args) {
		
		
		Sale sale = new Sale("#1", new Cashier("01", "Wafda"));
		sale.addSaleItem(new Item("001", 30000,"Telur", "Makanan", true), 2);
		sale.addSaleItem(new Item("002", 50000,"Beras", "Makanan", true), 1);
		String number = "#1";
		Sale result = null;
		
		Connection conn;
		try {
			conn = DriverManager.getConnection(jdbcUrl, userName, password);
			String sqlQuery = "SELECT * FROM sale WHERE sale_number = ?";
			
			PreparedStatement stm = conn.prepareStatement(sqlQuery);
			stm.setString(1, number);
			
			ResultSet rs = stm.executeQuery();
			while(rs.next()) {
				String sale_number = rs.getString("sale_number");
				Cashier cashier_id = findCashier(rs.getString("cashier_id"));
				result = new Sale(sale_number, cashier_id);
		}
			System.out.println("sale_number : "+ result.getSaleNumber());
			System.out.println("cashier_name : "+ result.getCashier().getCashierID());

			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
//		try {
//			Connection conn = null;
//			conn = DriverManager.getConnection(userName, password, null);
//			conn.setAutoCommit(false);
//			
////			String sql = "INSERT INTO sale(sale_number, trans_date, cashier_id) values (?,?, ?)";
////			PreparedStatement stm = conn.prepareStatement(sql);
////			
////			stm.setString(1, sale.getSaleNumber());
////			stm.setDate(2, new Date (sale.getTransDate().getTime()));
////			stm.setString(3, sale.getCashier().getCashierID());
////
////			stm.executeUpdate();
//			
//			
//	
//			conn.commit();
//			
//		} 	catch (Exception e) {
//					
//				}
	}
	
	public static Cashier findCashier(String cashier) {
		Cashier resultCashier = null;
		
		Connection conn = null;
		try {
			conn = DriverManager.getConnection(jdbcUrl, userName, password);
			String sqlQuery = "SELECT * FROM cashier WHERE cashier_id = ?";
			
			PreparedStatement stm;
			stm = conn.prepareStatement(sqlQuery);
			stm.setString(1, cashier);
			
			ResultSet rs = stm.executeQuery();
				if(rs.next()) {
					resultCashier = new Cashier(rs.getString("cashier_id"), rs.getString("cashier_name"));
				}
				
							
				
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}return resultCashier;
		
		
		
		
		
	} 
	
	

}
