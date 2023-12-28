package com.kkm.pos2.repository.impl;

import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import com.kkm.pos2.database.DatabaseConnection;
import com.kkm.pos2.domain.CashPayment;
import com.kkm.pos2.domain.Cashier;
import com.kkm.pos2.domain.Item;
import com.kkm.pos2.domain.Sale;
import com.kkm.pos2.domain.SaleItem;
import com.kkm.pos2.exception.DatabaseConnectionException;
import com.kkm.pos2.exception.RepositoryException;
import com.kkm.pos2.repository.ItemRepository;
import com.kkm.pos2.repository.SaleRepository;

public class SaleRepositoryMySQL implements SaleRepository{
	public static List<Sale> sales = new ArrayList<Sale>();
	protected ItemRepository itemRepository;
	
	@Override
	public void save(Sale sale) throws RepositoryException {
		//sales.add(sale);
		
		try (Connection conn = DatabaseConnection.conn()){
			conn.setAutoCommit(false);
			
			//insert sale
			String sql = "INSERT INTO sale(sale_number, trans_date, cashier_id) values (?,?,?)";
			PreparedStatement stm = conn.prepareStatement(sql);
			
			stm.setString(1, sale.getSaleNumber());
			stm.setDate(2, new Date (sale.getTransDate().getTime()));
			stm.setString(3, sale.getCashier().getCashierID());

			stm.executeUpdate();
			
			//insert sale_item
			String sql2 = "INSERT INTO sale_item(sale_number, item_code, quantity) values (?,?,?)";
			for(SaleItem s: sale.getSaleItems()) {
				
				PreparedStatement stm2 = conn.prepareStatement(sql2);
				
				stm2.setString(1, sale.getSaleNumber());
				stm2.setString(2, s.getItem().getItemCode());
				stm2.setInt(3, s.getQuantity());
				stm2.executeUpdate();
			}
			
			//insert payment
			String sql4 = "INSERT INTO payment(amount, isPay, sale_number) values (?,?,?)";
			PreparedStatement stm4 = conn.prepareStatement(sql4);
			
			stm4.setDouble(1, sale.totalPayment());
			stm4.setInt(2, 1);
			stm4.setString(3, sale.getSaleNumber());
			stm4.executeUpdate();
			
			if(sale.getPayment() instanceof CashPayment) {
				String sqlCash = "INSERT INTO cash_payment(cash_in_hand , payment) values (?,?)";
				PreparedStatement stmCash = conn.prepareStatement(sqlCash);
				stmCash.setDouble(1, ((CashPayment) sale.getPayment()).getCashInHand());
				stmCash.setString(2, sale.getSaleNumber());
				stmCash.executeUpdate();
			} else {
				String sqlQris = "INSERT INTO qris_payment(amount , payment) values (?,?)";
				PreparedStatement stmQris = conn.prepareStatement(sqlQris);
				stmQris.setDouble(1, sale.getPayment().getAmount());
				stmQris.setString(2, sale.getSaleNumber());
				stmQris.executeUpdate();
			}
			
			conn.commit();
			
		} 	catch (SQLException e) {
			throw new RepositoryException(e.getMessage());	
				} catch (DatabaseConnectionException e) {
					throw new RepositoryException(e.getMessage());
				}
			}
		
		
		
	

	@Override
	public Sale findByNumber(String number) throws RepositoryException {
		Sale result = null;
		
		try (Connection conn = DatabaseConnection.conn()){
			String sqlQuery = "SELECT * FROM sale WHERE sale_number = ?";
			
			PreparedStatement stm = conn.prepareStatement(sqlQuery);
			stm.setString(1, number);
			
			ResultSet rs = stm.executeQuery();
			if(rs.next()) {
				result = new Sale(rs.getString("sale_number"), findCashierById(rs.getString("cashier_id")));
		}
		
		}catch (SQLException e) {
			throw new RepositoryException(e.getMessage());
		} catch (DatabaseConnectionException e1) {
		throw new RepositoryException(e1.getMessage());
		}
				
		
		
		return result;
	}
	
//	public Item fetchItemByCode(String itemCode) {
//		
//			return itemRepository.findByCode(itemCode);
//	}
	
	public Cashier findCashierById(String cashier) throws RepositoryException{
		Cashier result = null;
		
		try (Connection conn = DatabaseConnection.conn()) {
			String sqlQuery = "SELECT * FROM cashier WHERE cashier_id = ?";
			
			PreparedStatement stm;
			stm = conn.prepareStatement(sqlQuery);
			stm.setString(1, cashier);
			
			ResultSet rs = stm.executeQuery();
				if(rs.next()) {
					result = new Cashier(rs.getString("cashier_id"), rs.getString("cashier_name"));
				}
			
				
		} catch (SQLException e) {
			throw new RepositoryException(e.getMessage());
		} catch (DatabaseConnectionException e1) {
			throw new RepositoryException(e1.getMessage());
		}return result;
		
		
		
		
		
	} 

		
	
	

}
