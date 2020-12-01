package de.dser.bewerber.be.aop.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "stock_in_bestand")
public class StockInBestand extends EntitySuperclass {
	
	private Delivery delivery;
	private String stockIdentifier;
	private Double valueInEuro;

	@ManyToOne(optional = false)
	@JoinColumn(name = "delivery_id")
	public Delivery getDelivery() {
		return delivery;
	}

	public void setDelivery(Delivery delivery) {
		this.delivery = delivery;
	}

	public void setStockIdentifier(String stockIdentifier) {
		this.stockIdentifier = stockIdentifier;
	}

	public void setValueInEuro(Double valueInEuro) {
		this.valueInEuro = valueInEuro;
	}

	@Column(name = "stock_identifier", nullable = false)
	public String getStockIdentifier() {
		return stockIdentifier;
	}

	@Column(name = "value_in_euro", nullable = false)
	public Double getValueInEuro() {
		return valueInEuro;
	}
	
}
