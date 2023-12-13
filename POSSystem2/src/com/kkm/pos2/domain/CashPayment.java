package com.kkm.pos2.domain;

public class CashPayment extends Payment{

	private double cashInHand;
	


	public CashPayment(double amount, double cashInHand) {
		super(amount);
		this.cashInHand = cashInHand;
	}


	public double getCashInHand() {
		return cashInHand;
	}

	
	public double change(){
		return cashInHand - this.amount;
	}


	@Override
	public boolean validate() {
		
		if (cashInHand > amount) {
			return true;
		} return false;
	}
	
	
	}
