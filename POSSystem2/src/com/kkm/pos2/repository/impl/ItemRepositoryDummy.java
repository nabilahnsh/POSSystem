package com.kkm.pos2.repository.impl;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import com.kkm.pos2.domain.Item;
import com.kkm.pos2.repository.ItemRepository;


public class ItemRepositoryDummy implements ItemRepository {

	
	private List<Item> addItemDummy(){
		List<Item> listItem = new ArrayList<Item>();
		listItem.add(new Item("001", 30000,"Telur", "Makanan", true));
		listItem.add(new Item("002", 50000,"Beras", "Makanan", true));
		listItem.add(new Item("003", 12000,"Spidol", "ATK", false));
		listItem.add(new Item("004", 1000,"Kertas HVS", "ATK", false));
		
		return listItem;
	}
	
	@Override
	public Item findByCode(String code) {
		List<Item> listItem = addItemDummy();
		
		Iterator<Item> it = listItem.iterator();
		while(it.hasNext()) {
			Item item = it.next();
			if (item.getItemCode().equals(code)) {
				return item;
			}
			
		}return null;
		
	} 

}
