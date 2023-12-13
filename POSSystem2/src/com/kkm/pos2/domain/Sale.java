package com.kkm.pos2.domain;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.Iterator;
import java.util.List;



public class Sale {
	private String saleNumber;
	private Date transDate = new Date();
	private List<SaleItem> saleItems = new ArrayList<SaleItem>();
	private Cashier cashier;
	private Payment payment;
	double totalPrice = 0;
	double totalTaxPayment = 0;
	double tax = 0;
	double totalPayment = 0;

	
	public Payment getPayment() {
		return payment;
	}
	
	public void setPayment(Payment payment) {
		this.payment = payment;
	}

	public Sale(String saleNumber, Cashier cashier) {
		this.saleNumber = saleNumber;
		this.cashier = cashier;
	}

	public void addSaleItem(Item item, int quantity) {
		SaleItem si = new SaleItem(item, quantity);
		
		saleItems.add(si);	
	}

	public String getSaleNumber() {
		return saleNumber;
	}

	public Date getTransDate() {
		return transDate;
	}

	public List<SaleItem> getSaleItems() {
		List<SaleItem> clonedList = new ArrayList<SaleItem>();
		clonedList.addAll(saleItems);
		return clonedList;
	}

	public Cashier getCashier() {
		return cashier;
	}
	
	public double grandTotal() {
	
//		for(SaleItem saleItem : saleItems) {
//			totalPrice = totalPrice + saleItem.totalPrice();
//		} return totalPrice;
		
		Iterator<SaleItem> it = saleItems.iterator();
		while(it.hasNext()) {
			SaleItem si = it.next();
			totalPrice = totalPrice + si.totalPrice();
			}
		return totalPrice;
	}
	
	public double totalTaxPayment() {
//		for(SaleItem saleItem : saleItems) {
//		if (saleItem.getItem().isTaxable() == true) {
//			totalTaxPayment = totalTaxPayment + saleItem.totalPrice();	
//		} 
//		} 
//		//tax = totalTaxPayment * 0.1; //pindahin ke constants
//		return totalTaxPayment;
		Iterator<SaleItem> it = saleItems.iterator();		
		while(it.hasNext()) {
			SaleItem si = it.next();
			if(si.getItem().isTaxable() == true) {
			totalTaxPayment = totalTaxPayment + si.totalPrice();
			tax = totalTaxPayment * Tax.tax;
			}
			} return tax;
	}
	
	public double totalPayment() {
		totalPayment = totalPrice + tax ;
		return totalPayment;
	}
	
	public boolean finish() {
		if(this.getPayment().validate()) {
			return true;
		} else {
			return false;
		}
	}

	
	
	
	
	
	
}
