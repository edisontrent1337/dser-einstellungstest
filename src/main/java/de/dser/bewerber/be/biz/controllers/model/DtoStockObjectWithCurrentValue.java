package de.dser.bewerber.be.biz.controllers.model;

import java.util.Calendar;

public class DtoStockObjectWithCurrentValue extends DtoStockAsset {
	public static DtoStockObjectWithCurrentValue build(DtoStockAsset stockObject, double value, DtoStockPrice stockPrice) {
		DtoStockObjectWithCurrentValue ret = new DtoStockObjectWithCurrentValue();
		ret.setAmount(stockObject.getAmount());
		ret.setCurrency(stockPrice.getCurrency());
		ret.setCurrentValue(value);
		ret.setDateOfCurrentValue(stockPrice.getDateOfPrice());
		ret.setIsin(stockObject.getIsin());
		ret.setWkn(stockObject.getWkn());
		
		return ret;
	}
	private Double currentValue;
	private Calendar dateOfCurrentValue;
	private String currency;
	
	public String getCurrency() {
		return currency;
	}
	public void setCurrency(String currency) {
		this.currency = currency;
	}
	public Double getCurrentValue() {
		return currentValue;
	}
	public void setCurrentValue(Double currentValue) {
		this.currentValue = currentValue;
	}
	public Calendar getDateOfCurrentValue() {
		return dateOfCurrentValue;
	}
	public void setDateOfCurrentValue(Calendar dateOfCurrentValue) {
		this.dateOfCurrentValue = dateOfCurrentValue;
	}
}
