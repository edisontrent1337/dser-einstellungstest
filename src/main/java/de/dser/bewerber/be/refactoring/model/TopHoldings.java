package de.dser.bewerber.be.refactoring.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class TopHoldings implements Serializable, Cloneable {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1104435933740550853L;

	/** Länder */
	private List<TopHolding> countries;
	
	/** Branchen */
	private List<TopHolding> branches;
	
	/** Währungen */
	private List<TopHolding> currencies;
	
	/** Kategorien */
	private List<TopHolding> categories;

	/**
	 * Gets the countries.
	 * 
	 * @return the countries
	 */
	
	
	@Override
	protected Object clone() {
		TopHoldings topHoldings = new TopHoldings();
		topHoldings.setBranches(cloneTopHoldings(getBranches()));
		topHoldings.setCategories(cloneTopHoldings(getCategories()));
		topHoldings.setCountries(cloneTopHoldings(getCountries()));
		topHoldings.setCurrencies(cloneTopHoldings(getCountries()));
		return topHoldings;
	}


	/**
	 * @return the countries
	 */
	public List<TopHolding> getCountries() {
		return countries;
	}


	/**
	 * @param countries the countries to set
	 */
	public void setCountries(List<TopHolding> countries) {
		this.countries = countries;
	}


	/**
	 * @return the branches
	 */
	public List<TopHolding> getBranches() {
		return branches;
	}


	/**
	 * @param branches the branches to set
	 */
	public void setBranches(List<TopHolding> branches) {
		this.branches = branches;
	}


	/**
	 * @return the currencies
	 */
	public List<TopHolding> getCurrencies() {
		return currencies;
	}


	/**
	 * @param currencies the currencies to set
	 */
	public void setCurrencies(List<TopHolding> currencies) {
		this.currencies = currencies;
	}


	/**
	 * @return the categories
	 */
	public List<TopHolding> getCategories() {
		return categories;
	}


	/**
	 * @param categories the categories to set
	 */
	public void setCategories(List<TopHolding> categories) {
		this.categories = categories;
	}


	public List<TopHolding> cloneTopHoldings(List<TopHolding> topHoldings){
		List<TopHolding> clonedTopHoldings = new ArrayList<TopHolding>(topHoldings.size());
		for(TopHolding original : topHoldings)
			clonedTopHoldings.add(original.clone());
		return clonedTopHoldings;
	}
		

}
