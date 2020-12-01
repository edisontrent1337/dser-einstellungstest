package de.dser.bewerber.be.refactoring.model;

public enum StockType {
	/**
	 * All other than specified
	 */
	Other,
	
	/**
	 * Ein Wertpapier mit einem realtiven Kurs
	 */
	Relative,
	
	/** 
	 * Ein modelliertes Wertpapier
	 **/
	ModeledStock,
	
	/**
	 * TODO Ein Wertpapier welches, ...
	 */
	NonFungible, MODELED_STOCK, NONFUNGIBLE, BOND,
}
