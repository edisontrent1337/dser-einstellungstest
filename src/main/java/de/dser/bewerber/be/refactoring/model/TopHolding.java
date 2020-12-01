package de.dser.bewerber.be.refactoring.model;

import java.io.Serializable;

public class TopHolding implements Serializable, Cloneable {


	/**
	 * 
	 */
	private static final long serialVersionUID = -4110524431551271730L;
	
	private long				id;
	/**
	 * Schl
	 */
	private String				name;
	/**
	 * prozentualer Anteil am Key (Basis 1)
	 */
	private double				holding;

	
	public TopHolding() {
		super();
	}

	/**
	 * @param key
	 * @param holding
	 */
	public TopHolding(long id, String key, double holding) {
		super();
		this.id = id;
		this.name = key;
		this.holding = holding;
	}

	/**
	 * @return the key
	 */
	public String getName() {
		return name;
	}

	/**
	 * @param key
	 *            the key to set
	 */
	public void setName(String key) {
		this.name = key;
	}

	/**
	 * @return the holding
	 */
	public double getHolding() {
		return holding;
	}

	/**
	 * @param holding
	 *            the holding to set
	 */
	public void setHolding(double holding) {
		this.holding = holding;
	}

	/**
	 * @return the id
	 */
	public long getId() {
		return id;
	}

	/**
	 * @param id the id to set
	 */
	public void setId(long id) {
		this.id = id;
	}

	@Override
	public TopHolding clone() {
		TopHolding topHolding = new TopHolding(this.id, this.name,this.holding);
		return topHolding;
	}
}
