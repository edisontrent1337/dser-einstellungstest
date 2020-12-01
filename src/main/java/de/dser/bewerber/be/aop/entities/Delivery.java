package de.dser.bewerber.be.aop.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import java.sql.Date;

@Entity
@Table(name = "delivery", uniqueConstraints = {@UniqueConstraint(columnNames = {Delivery.COLUMN_DEPOT_ID, Delivery.COLUMN_DELIVERY_DATE})})
public class Delivery extends EntitySuperclass {
	
	static final String COLUMN_DEPOT_ID = "depot_id";
	static final String COLUMN_DELIVERY_DATE = "delivery_date";
	private Depot depot;
	private Date date;

	@ManyToOne(optional = false)
	@JoinColumn(name = COLUMN_DEPOT_ID)
	public Depot getDepot() {
		return depot;
	}

	public void setDepot(Depot depot) {
		this.depot = depot;
	}

	@Column(name = COLUMN_DELIVERY_DATE, nullable = false)
	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}
}
