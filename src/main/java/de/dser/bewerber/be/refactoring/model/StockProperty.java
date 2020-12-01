package de.dser.bewerber.be.refactoring.model;

import java.io.Serializable;

public class StockProperty  implements Serializable {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = -2698002220035192041L;

	private Long id;
	
	private String name;
	
	/**
	 * Create an empty instance
	 */
	public StockProperty() {
		super();
	}

	/**
	 * Instantiates a new stock property.
	 * 
	 * @param id the id
	 * @param name the name
	 */
	public StockProperty(Long id, String name) {
		super();
		this.id = id;
		this.name = name;
	}

	/**
	 * Gets the id.
	 * 
	 * @return the id
	 */
	public Long getId() {
		return id;
	}

	/**
	 * Sets the id.
	 * 
	 * @param id the new id
	 */
	public void setId(Long id) {
		this.id = id;
	}

	/**
	 * @return the name
	 */
	public String getName() {
		return this.name;
	}

	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}
	
	/* (non-Javadoc)
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		if (obj instanceof StockProperty) {
			StockProperty mobj = (StockProperty) obj;
			if (getId() == null) return false;
			return getId().equals(mobj.getId());
		}
		return false;
	}
}
