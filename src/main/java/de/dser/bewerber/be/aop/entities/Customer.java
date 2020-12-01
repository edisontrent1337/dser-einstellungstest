package de.dser.bewerber.be.aop.entities;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "customer")
public class Customer extends EntitySuperclass {
	
	private Bank bank;

	@ManyToOne(optional = false)
	@JoinColumn(name = "bank_id")
	public Bank getBank() {
		return bank;
	}

	public void setBank(Bank bank) {
		this.bank = bank;
	}
	
	

}
