package de.dser.bewerber.be.aop.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

@Entity
@Table(name = "depot", uniqueConstraints = {@UniqueConstraint(columnNames = "account_number")})
public class Depot extends EntitySuperclass {
	
	private Customer customer;
	private String accountNumber;

	@ManyToOne(optional = false)
	@JoinColumn(name = "customer_id")
	public Customer getCustomer() {
		return customer;
	}

	public void setCustomer(Customer customer) {
		this.customer = customer;
	}

	@Column(name = "account_number", nullable = false, unique = true)
	public String getAccountNumber() {
		return accountNumber;
	}

	public void setAccountNumber(String accountNumber) {
		this.accountNumber = accountNumber;
	}
}
