package com.kkm.pos2.repository.impl;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import com.kkm.pos2.domain.Item;
import com.kkm.pos2.repository.ItemRepository;

public class ItemRepositoryMySQL implements ItemRepository{
	List<Item> listItem = new ArrayList<Item>();
	String userName = "root";
	String password = "";
	String jdbcUrl = "jdbc:mysql://localhost:3306/pos2_db";
	
	
	private List<Item> findAll() {	
		try {
			//load driver
			Class.forName("com.mysql.jdbc.Driver");
			
			//get db connection -- jdbc url
			Connection conn = DriverManager.getConnection(jdbcUrl, userName, password);
			String sqlQuery = "SELECT * FROM item";
			
			Statement stm = conn.createStatement();
			ResultSet rs = stm.executeQuery(sqlQuery);
			
			//add item
			while(rs.next()) {
				boolean isTaxable;
				if(rs.getInt("taxable") == 1) {
					isTaxable = true;
				} else {
					isTaxable = false;
				}
				listItem.add(new Item(rs.getString("item_code"), rs.getDouble("price"), rs.getString("description"), rs.getString("type"), isTaxable));
			}
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} return listItem;
	}
	@Override
	public Item findByCode(String code) {
		Item result = null;
		
		try {
			Connection conn = DriverManager.getConnection(jdbcUrl, userName, password);
			String sqlQuery = "SELECT * FROM item WHERE item_code = ?";
			
			PreparedStatement stm = conn.prepareStatement(sqlQuery);
			stm.setString(1, code);
			
			ResultSet rs = stm.executeQuery();
				if(rs.next()) {
					result = new Item(rs.getString("item_code"), rs.getDouble("price"), rs.getString("description"), rs.getString("type"), rs.getBoolean("taxable"));
				}
			
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}	
		return result;
	}

}
