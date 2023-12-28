package com.kkm.pos2.domain;

public class QrisPayment extends Payment {
	
	public void generateQR() {
		System.out.println("Please Scan This QR . . .");
	}

	@Override
	public void validate() {
		this.amount = sale.totalPayment();
		this.isPay = true;
	}

	@Override
	public void finishSale() {
		System.out.println("Payment (QRIS) : " + amount + "\n");
		
	}
	
}
