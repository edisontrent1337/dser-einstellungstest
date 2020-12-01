package de.dser.bewerber.be.refactoring.model;

import java.util.ArrayList;
import java.util.List;

public class StockCategory {
private static final long serialVersionUID = -3878410888276160450L;
	
	public static final StockCategory SEARCH_CATEGORY = new StockCategory(0l, "Suchergebnisse");

	private Long id;

	private String name;
	
	private StockCategory parent;
	
	private List<StockCategory> childs = new ArrayList<StockCategory>(0);
	
	private int stockAmount = 0;
	
	public StockCategory() {
		super();
	}
	
	public StockCategory(Long id, String name) {
		this.id = id;
		this.name = name;
	}

	public StockCategory(Long id, String name, StockCategory parent) {
		super();
		this.id = id;
		this.name = name;
		this.parent = parent;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}

	public StockCategory getParent() {
		return parent;
	}

	public void setParent(StockCategory parent) {
		this.parent = parent;
	}

	public List<StockCategory> getChilds() {
		if (this.childs == null) this.childs = new ArrayList<StockCategory>();
		return childs;
	}

	public void setChilds(List<StockCategory> childs) {
		this.childs = childs;
	}

	public int getStockAmount() {
		return stockAmount;
	}

	public void setStockAmount(int stockAmount) {
		this.stockAmount = stockAmount;
	}

	@Override
	public boolean equals(Object obj) {
		if (obj instanceof StockCategory) {
			return (getId() != null && ((StockCategory) obj).getId() != null && ((StockCategory) obj).getId().equals(this.getId()));
		}
		return false;
	}
	
	@Override
	public int hashCode() {
		return new String(StockCategory.class.getName()+":"+id).hashCode();
	}
	
	@Override
	public String toString() {
		return "StockCategory: "+name;
	}
	
	@Override
	public StockCategory clone() {
		StockCategory category = new StockCategory(getId(), name, getParent());
		if (getChilds() != null) {
			List<StockCategory> childs = new ArrayList<StockCategory>();
			for (StockCategory child : getChilds()) {
				childs.add(child.clone());
			}
			category.setChilds(childs);
		}
		return category;
	}
}
