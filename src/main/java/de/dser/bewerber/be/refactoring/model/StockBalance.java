package de.dser.bewerber.be.refactoring.model;

import java.io.Serializable;

public class StockBalance implements Serializable {
	private static final long serialVersionUID = -1292482825393289762L;

	private double amount;
	
	private double balance;

	public StockBalance() {
		super();
	}

	public StockBalance(double amount, double balance) {
		super();
		this.amount = amount;
		this.balance = balance;
	}

	public double getAmount() {
		return amount;
	}

	public void setAmount(double amount) {
		this.amount = amount;
	}

	public double getBalance() {
		return balance;
	}

	public void setBalance(double balance) {
		this.balance = balance;
	}
	
}
