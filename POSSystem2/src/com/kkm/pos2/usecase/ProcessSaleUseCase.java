package com.kkm.pos2.usecase;

import com.kkm.pos2.domain.CashPayment;
import com.kkm.pos2.domain.Cashier;
import com.kkm.pos2.domain.Item;
import com.kkm.pos2.domain.Payment;
import com.kkm.pos2.domain.QrisPayment;
import com.kkm.pos2.domain.Sale;
import com.kkm.pos2.exception.RepositoryException;
import com.kkm.pos2.exception.UseCaseException;
import com.kkm.pos2.repository.ItemRepository;
import com.kkm.pos2.repository.SaleRepository;
import com.kkm.pos2.repository.impl.ItemRepositoryDummy;
import com.kkm.pos2.repository.impl.ItemRepositoryFile;
import com.kkm.pos2.repository.impl.ItemRepositoryMySQL;
import com.kkm.pos2.repository.impl.SaleRepositoryDummy;
import com.kkm.pos2.repository.impl.SaleRepositoryMySQL;

public class ProcessSaleUseCase {
	private Sale sale;
	private ItemRepository itemRepository;
	private SaleRepository saleRepository;
	
	public ProcessSaleUseCase() {
		itemRepository = new ItemRepositoryMySQL();
		saleRepository = new SaleRepositoryMySQL();
	}
	
	public void createNewSale(String saleNumber, Cashier cashierName) {
		this.sale = new Sale(saleNumber, cashierName);
	}
	
	public void addSaleItem(String itemCode, int quantity) {
		Item item = itemRepository.findByCode(itemCode);

		this.sale.addSaleItem(item, quantity);
		
	}
	
	public ProcessSaleUseCase makePayment(Payment payment) {
		this.sale.setPayment(payment);
		payment.setSale(sale);
		return this;	
		}
	
	

	public Sale getSale() {
		return sale;
	}
	public void finishSale()  throws UseCaseException {
		System.out.println("\n" + "=============================================================" + "\n");
		sale.getPayment().validate();
//		
//		try {
//			
			saleRepository.save(sale);
//		} catch (RepositoryException e) {
//			throw new UseCaseException(e.getMessage());
//		}
		
		getSale();
		sale.getPayment().finishSale();
		System.out.println("=============================================================" + "\n");	
	}
	
	public Payment qris() {
		return new QrisPayment();
	}
	
	public Payment cash(int amount) {
		return new CashPayment(amount);
	}
	
	
}
