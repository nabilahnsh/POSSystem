package com.kkm.pos2.domain;

public abstract class Payment {
	protected double amount;
	protected Sale sale;
	
	
	public Payment(double amount) {
		this.amount = amount;
	}
	

		public double getAmount() {
		return amount;
	}

	public abstract boolean validate();
	
	
}
