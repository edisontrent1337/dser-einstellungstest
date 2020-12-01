package de.dser.bewerber.be.refactoring.model;

import java.util.Date;
import java.util.List;

public interface IStock {
public Long getUpdateId();
	
	public StockCategory getCategory();
	
	public String getBankProductType();
	
	public StockType getType();
	
	public Date getFirstDate();
	
	public String getFolder();
	
	public String getArea();
	
	public StockProperty getBranche();
	
	public String getEmitter();
	
	public int getRating();
	
	public String getIsin();
	
	public Date getLastDate();
	
	public Double getLastPrice();
	
	public Double getOriginaleLastPrice();
	
	public StockProperty getCurrency();
	
	public StockProperty getCountry();
	
	public String getName();
	
	public Integer getRiskClass();
	
	public String getWkn();

	public void setOptimizeAble(boolean optimizeAble);
	
	public boolean isoptimizeable();
	
	public TopHoldings getTopHoldings();
	
	public Date getRuntimeEnd();

	public PayoutType getPayoutType();
	
	public Double getCoupon();

	public StockProperty getInvestmentPeriod();

	public Boolean getMemberOfStockUniverse();
	
	public Boolean getCertificate();
	
	public Date getCertificateSubscriptionStart();
	
	public Date getCertificateSubscriptionFinish();
	
	public Date getTradingStart();
	
	public Date getTradingEnd();
	
	public Date getSaleStart();
	
	public Boolean getStock();
	
	public Boolean getBond();
	
	public List<StockCategoryType> getStockCategoryTypes();
}
