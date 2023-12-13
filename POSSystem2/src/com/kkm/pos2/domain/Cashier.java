package com.kkm.pos2.domain;

public class Cashier {

	private String cashierID;
	private String cashierName;
	
	public Cashier(String cashierID, String cashierName) {
		this.cashierID = cashierID;
		this.cashierName = cashierName;
	}

	public String getCashierID() {
		return cashierID;
	}

	public String getCashierName() {
		return cashierName;
	}
	
	
	
	
}
