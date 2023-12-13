package com.kkm.pos2.usecase;

import com.kkm.pos2.domain.Cashier;
import com.kkm.pos2.domain.Item;
import com.kkm.pos2.domain.Payment;
import com.kkm.pos2.domain.Sale;
import com.kkm.pos2.repository.ItemRepository;
import com.kkm.pos2.repository.SaleRepository;
import com.kkm.pos2.repository.impl.ItemRepositoryDummy;
import com.kkm.pos2.repository.impl.ItemRepositoryFile;
import com.kkm.pos2.repository.impl.ItemRepositoryMySQL;
import com.kkm.pos2.repository.impl.SaleRepositoryDummy;

public class ProcessSaleUseCase {
	private Sale sale;
	private ItemRepository itemRepository;
	private SaleRepository saleRepository;
	
	public ProcessSaleUseCase() {
		itemRepository = new ItemRepositoryMySQL();
		saleRepository = new SaleRepositoryDummy();
	}
	
	public void createNewSale(String saleNumber, Cashier cashierName) {
		this.sale = new Sale(saleNumber, cashierName);
	}
	
	public void addSaleItem(String itemCode, int quantity) {
		Item item = itemRepository.findByCode(itemCode);

		this.sale.addSaleItem(item, quantity);
		
	}
	
	public boolean makePayment(Payment payment) {
		this.sale.setPayment(payment);
		return this.sale.finish();	
		}
	
	public Sale finishSale() {
		if(this.sale.getPayment().validate()) {
			saleRepository.save(this.sale);
		}
		return saleRepository.findByNumber(this.sale.getSaleNumber());
	}

	public Sale getSale() {
		return sale;
	}
	
	
}
