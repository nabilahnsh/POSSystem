package com.kkm.pos2.repository.impl;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import com.kkm.pos2.domain.Item;
import com.kkm.pos2.repository.ItemRepository;

public class ItemRepositoryMySQL implements ItemRepository{

	private List<Item> findAll() {
		List<Item> listItem = new ArrayList<Item>();
		String userName = "root";
		String password = "";
		String jdbcUrl = "jdbc:mysql://localhost:3306/pos2_db";
		
		//load driver
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
		List<Item> listItem = findAll();
		
		Iterator<Item> it = listItem.iterator();
		while(it.hasNext()) {
			Item item = it.next();
			if (item.getItemCode().equals(code)) {
				return item;
			}
		
	}return null;
	}

}
