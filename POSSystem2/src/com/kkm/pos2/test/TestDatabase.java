package com.kkm.pos2.test;

import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import com.kkm.pos2.domain.CashPayment;
import com.kkm.pos2.domain.Cashier;
import com.kkm.pos2.domain.Item;
import com.kkm.pos2.domain.Sale;
import com.kkm.pos2.domain.SaleItem;

public class TestDatabase {
	
	
	public static void main(String[] args) {
		String userName = "root";
		String password = "";
		String jdbcUrl = "jdbc:mysql://localhost:3306/pos2_db";
		Connection conn = null;
		
		Sale sale = new Sale("#1", new Cashier("01", "Wafda"));
		sale.addSaleItem(new Item("001", 30000,"Telur", "Makanan", true), 2);
		sale.addSaleItem(new Item("002", 50000,"Beras", "Makanan", true), 1);
		sale.setPayment(new CashPayment(150000));
		
		saleRepository
		try {
			conn = DriverManager.getConnection(jdbcUrl, userName, password);
			conn.setAutoCommit(false);
			
			String sql = "INSERT INTO sale(sale_number, trans_date, cashier_id) values (?,?, ?)";
			PreparedStatement stm = conn.prepareStatement(sql);
			
			stm.setString(1, sale.getSaleNumber());
			stm.setDate(2, new Date (sale.getTransDate().getTime()));
			stm.setString(3, sale.getCashier().getCashierID());

			stm.executeUpdate();
			
			String sql2 = "INSERT INTO sale_item(sale_number, item_code, quantity) values (?,?,?)";
			for(SaleItem s: sale.getSaleItems()) {
				
				PreparedStatement stm2 = conn.prepareStatement(sql2);
				
				stm2.setString(1, sale.getSaleNumber());
				stm2.setString(2, s.getItem().getItemCode());
				stm2.setInt(3, s.getQuantity());
				stm2.executeUpdate();
			}
			
			String sql3 = "INSERT INTO cashier(cashier_id, cashier_name) values (?,?)";
			PreparedStatement stm3 = conn.prepareStatement(sql3);	
			
			stm3.setString(1, sale.getCashier().getCashierID());
			stm3.setString(2, sale.getCashier().getCashierName());
			
			stm3.executeUpdate();
			
			String sql4 = "INSERT INTO payment(amount, isPay, sale_number) values (?,?,?)";
			PreparedStatement stm4 = conn.prepareStatement(sql4);
			
			stm4.setDouble(1, sale.totalPayment());
			stm4.setInt(2, 1);
			stm4.setString(3, sale.getSaleNumber());
			stm4.executeUpdate();
	
			conn.commit();
			
		} 	catch (SQLException e1) {
					
				}
			}
	
	
	

}
