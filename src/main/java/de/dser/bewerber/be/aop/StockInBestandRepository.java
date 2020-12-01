package de.dser.bewerber.be.aop;

import org.hibernate.Session;

import java.util.Map;

public class StockInBestandRepository {

	private final Session session;

	public StockInBestandRepository(Session session) {
		this.session = session;
	}

	public Map<String, Double> loadValueOfDepotsForBank(Long bankId, java.sql.Date maximumDeliveryDate) {
		throw new UnsupportedOperationException("Implement me!!");
	}

}
