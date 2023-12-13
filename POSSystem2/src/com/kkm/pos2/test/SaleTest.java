package com.kkm.pos2.test;

import java.util.Iterator;

import com.kkm.pos2.domain.CashPayment;
import com.kkm.pos2.domain.Payment;
import com.kkm.pos2.domain.QrisPayment;
import com.kkm.pos2.domain.Cashier;
import com.kkm.pos2.domain.Item;
import com.kkm.pos2.domain.Sale;
import com.kkm.pos2.domain.SaleItem;
import com.kkm.pos2.usecase.ProcessSaleUseCase;

public class SaleTest {
	
	public static void printSale(Sale sale) {
		System.out.println("================================================");
		System.out.println("Sale Number : " + sale.getSaleNumber());
		System.out.println("Cashier : " +sale.getCashier().getCashierName());
		System.out.println("Date : "+ sale.getTransDate().toString());
		
		System.out.println("Item : ");
		Iterator<SaleItem> itSaleItem = sale.getSaleItems().iterator();
		while(itSaleItem.hasNext()){
				SaleItem saleItem = itSaleItem.next();
				System.out.println("Item Code : "+ saleItem.getItem().getItemCode() + " | Description : "+ saleItem.getItem().getDescription()
						+" | Type : "+saleItem.getItem().getType()+" | Price : "+saleItem.getItem().getItemPrice()+" | Quantity : "+saleItem.getQuantity()
						+" | Total Price : "+saleItem.totalPrice());
			}
		
		System.out.println();
		System.out.println("Grand Total : "+ sale.grandTotal());
		System.out.println("Tax : "+ sale.totalTaxPayment());
		System.out.println("Total Price + Tax : "+ sale.totalPayment()+ "\n");

		}
	
	public static void printPayment(Sale sale) {
		if(sale.getPayment() instanceof CashPayment) {
			CashPayment cashPayment = (CashPayment) sale.getPayment();
			System.out.println("Payment (Cash) : " + cashPayment.getCashInHand());
			System.out.println("Change : " + cashPayment.change());
		}
		else {
			System.out.println("Payment (Qris) : " + sale.getPayment().getAmount());
		}
		System.out.println();
	System.out.println("================================================");

	}
	
	public static void main(String[] args) {
		
		Cashier cashier1 = new Cashier("01", "Wafda");
		Cashier cashier2 = new Cashier("02", "Alisa");
		

		
		ProcessSaleUseCase saleUseCase = new ProcessSaleUseCase();
		
		//Sale1
		saleUseCase.createNewSale("#1", cashier1);
		saleUseCase.addSaleItem("001", 2);
		saleUseCase.addSaleItem("002", 1);
		saleUseCase.addSaleItem("003", 2);

		printSale(saleUseCase.getSale());
		
		double amount1 = saleUseCase.getSale().totalPayment();
		Payment payment1 = new QrisPayment(amount1);
		
		
		if(saleUseCase.makePayment(payment1)) {
			Sale sale1 = saleUseCase.finishSale();
			
			printPayment(sale1);
		}
		
		//Sale2
		saleUseCase.createNewSale("#2", cashier2);
		saleUseCase.addSaleItem("004", 10);
		saleUseCase.addSaleItem("003", 4);
		
		printSale(saleUseCase.getSale());
		
		double amount2 = saleUseCase.getSale().totalPayment();
		Payment payment2 = new CashPayment(amount2,100_000);
		
		if(saleUseCase.makePayment(payment2)) {
			Sale sale2 = saleUseCase.finishSale();
			printPayment(sale2);
		}
		
		//Sale3
		saleUseCase.createNewSale("#3", cashier2);
		saleUseCase.addSaleItem("001", 2);
		saleUseCase.addSaleItem("004", 4);
		saleUseCase.addSaleItem("002", 1);
					
		printSale(saleUseCase.getSale());
					
		double amount3 = saleUseCase.getSale().totalPayment();
		Payment payment3 = new CashPayment(amount3,100_000);
					
		if(saleUseCase.makePayment(payment3)) {
			Sale sale3 = saleUseCase.finishSale();
			printPayment(sale3);
			}
		
		
				
		
	}
	
	


}
