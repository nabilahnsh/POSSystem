package com.kkm.pos2.domain;

public class QrisPayment extends Payment {
	
	
	public QrisPayment(double amount) {
		super(amount);
	}

	public String generateQr() {
		return "Processing QRIS Payment....";
	}

	@Override
	public boolean validate() {
		if(generateQr().equals("Processing QRIS Payment....") ) {
			//System.out.println("QRIS Payment Successfull");
			return true;
		}  
			else {
			//System.out.println("QRIS Payment Failed");
			return false;
		}
	}
	
}
