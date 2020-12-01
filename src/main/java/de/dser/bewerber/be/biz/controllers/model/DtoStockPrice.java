package de.dser.bewerber.be.biz.controllers.model;

import java.util.Calendar;

public class DtoStockPrice {

	private Calendar dateOfPrice;
	private Double price;
	private String currency;
	private String wkn;
	
	public String getWkn() {
		return wkn;
	}
	public void setWkn(String wkn) {
		this.wkn = wkn;
	}
	public String getCurrency() {
		return currency;
	}
	public void setCurrency(String currency) {
		this.currency = currency;
	}
	public Calendar getDateOfPrice() {
		return dateOfPrice;
	}
	public void setDateOfPrice(Calendar dateOfPrice) {
		this.dateOfPrice = dateOfPrice;
	}
	public Double getPrice() {
		return price;
	}
	public void setPrice(Double price) {
		this.price = price;
	}
}
