package de.dser.bewerber.be.aop;

import java.sql.Date;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import org.hibernate.cfg.Environment;
import org.hibernate.dialect.HSQLDialect;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import de.dser.bewerber.be.aop.entities.Bank;
import de.dser.bewerber.be.aop.entities.Customer;
import de.dser.bewerber.be.aop.entities.Delivery;
import de.dser.bewerber.be.aop.entities.Depot;
import de.dser.bewerber.be.aop.entities.EntitySuperclass;
import de.dser.bewerber.be.aop.entities.StockInBestand;

public class StockInBestandRepositoryTest {
	
	private SessionFactory sessionFactory;
	private StockInBestandRepository repository;
	private Session session;
	private Transaction transaction;

	/**
	 * Create SessionFactory and StockInBestandRepository
	 */
	@Before
	public void setUp() {
		sessionFactory = new Configuration()
				.addAnnotatedClass(StockInBestand.class)
				.addAnnotatedClass(Delivery.class)
				.addAnnotatedClass(Depot.class)
				.addAnnotatedClass(Customer.class)
				.addAnnotatedClass(Bank.class)
				.setProperty(Environment.SHOW_SQL, "true")
				.setProperty(Environment.FORMAT_SQL, "true")
				.setProperty(Environment.HBM2DDL_AUTO, "create-drop")
				.setProperty(Environment.URL, "jdbc:hsqldb:mem:/bewerber_einstellungstest")
				.setProperty(Environment.DRIVER, org.hsqldb.jdbcDriver.class.getName())
				.setProperty(Environment.USER, "sa")
				.setProperty(Environment.PASS, "")
				.setProperty(Environment.DIALECT, HSQLDialect.class.getName())
				.buildSessionFactory();

		session = sessionFactory.openSession();
		transaction = session.beginTransaction();

		repository = new StockInBestandRepository(session);
	}
	
	@After
	public void tearDown() {
		if(transaction != null) {
			try {
				transaction.rollback();
			} catch (Throwable throwable) {
				//do nothing
			}
		}
		if(session != null) {
			try {
				session.close();
			} catch (Throwable throwable) {
				//do nothing
			}
		}
		if(sessionFactory != null) {
			sessionFactory.close();
		}
	}
	
	/**
	 * Load all depots with bestand value for a particular date for a particular bank where the depot has no bestand-delivery.
	 */
	@Test
	public void testLoadValueOfDepotsForBank_DepotHasNoDeliveries() {
		Bank bank = new Bank();
		Customer customer = createCustomer(bank);
		Depot depot = createDepot(customer, "depot-1");
		
		persist(Arrays.asList(bank, customer, depot));
		
		Map<String, Double> valueByDepotNumber = repository.loadValueOfDepotsForBank(bank.getId(), java.sql.Date.valueOf("2100-01-01"));
		HashMap<String, Double> expected = new HashMap<String, Double>();
		expected.put(depot.getAccountNumber(), 0.0);
		
		Assert.assertEquals(expected, valueByDepotNumber);
	}
	
	/**
	 * Load all depots with bestand value for a particular date for a particular bank where the depot has a bestand-delivery, but that bestand does not contain any stocks.
	 */
	@Test
	public void testLoadValueOfDepotsForBank_DeliveryHasNoBestand() {
		Bank bank = new Bank();
		createCustomer(bank);
		Customer customer = createCustomer(bank);
		createCustomer(bank);
		Depot depot = createDepot(customer, "depot-1");
		Delivery delivery = createDelivery(depot, java.sql.Date.valueOf("2009-01-01"));
		
		persist(Arrays.asList(bank, customer, depot, delivery));
		
		Map<String, Double> valueByDepotNumber = repository.loadValueOfDepotsForBank(bank.getId(), java.sql.Date.valueOf("2010-01-01"));
		HashMap<String, Double> expected = new HashMap<String, Double>();
		expected.put(depot.getAccountNumber(), 0.0);
		
		Assert.assertEquals(expected, valueByDepotNumber);
	}
	
	/**
	 * Load all depots with bestand value for a particular date for a particular bank where the depot has a bestand but the only bestand occurs after the maximum delivery-date.
	 */
	@Test
	public void testLoadValueOfDepotsForBank_DepotHasOnlyDeliveriesAfterDate() {
		Bank bank = new Bank();
		Customer customer = createCustomer(bank);
		Depot depot = createDepot(customer, "depot-1");
		Delivery delivery = createDelivery(depot, java.sql.Date.valueOf("2015-01-01"));
		StockInBestand bestand1 = createStockInBestand(delivery, "710000", 1000);
		StockInBestand bestand2 = createStockInBestand(delivery, "750000", 2000);
		
		persist(Arrays.asList(bank, customer, depot, delivery, bestand1, bestand2));
		
		Map<String, Double> valueByDepotNumber = repository.loadValueOfDepotsForBank(bank.getId(), java.sql.Date.valueOf("2010-01-01"));
		HashMap<String, Double> expected = new HashMap<String, Double>();
		expected.put(depot.getAccountNumber(), 0.0);
		
		Assert.assertEquals(expected, valueByDepotNumber);
	}
	
	/**
	 * Load all depots with bestand value for a particular date for a particular bank. The depot has deliveries both before and after the maximum date.
	 */
	@Test
	public void testLoadValueOfDepotsForBank_DoesNotFindDeliveryAfterDate() {
		Bank bank = new Bank();
		Customer customer = createCustomer(bank);
		Depot depot = createDepot(customer, "depot-1");
		Delivery deliveryAfterDate = createDelivery(depot, java.sql.Date.valueOf("2015-01-01"));
		StockInBestand bestand1 = createStockInBestand(deliveryAfterDate, "710000", 1000);
		StockInBestand bestand2 = createStockInBestand(deliveryAfterDate, "750000", 2000);

		Delivery deliveryBeforeDate = createDelivery(depot, java.sql.Date.valueOf("2009-01-01"));
		StockInBestand bestand3 = createStockInBestand(deliveryBeforeDate, "710000", 5000);
		StockInBestand bestand4 = createStockInBestand(deliveryBeforeDate, "750000", 8000);
		
		persist(Arrays.asList(bank, customer, depot, deliveryAfterDate, deliveryBeforeDate, bestand1, bestand2, bestand3, bestand4));
		
		Map<String, Double> valueByDepotNumber = repository.loadValueOfDepotsForBank(bank.getId(), java.sql.Date.valueOf("2010-01-01"));
		HashMap<String, Double> expected = new HashMap<String, Double>();
		expected.put(depot.getAccountNumber(), 13000.0);
		
		Assert.assertEquals(expected, valueByDepotNumber);
	}
	
	/**
	 * Load all depots with bestand value for a particular date for a particular bank. There are multiple depots.
	 */
	@Test
	public void testLoadValueOfDepotsForBank_MultipleDepots() {
		Bank bank = new Bank();
		Customer customer = createCustomer(bank);
		Depot depot1 = createDepot(customer, "depot-1");
		Delivery deliveryOnDate = createDelivery(depot1, java.sql.Date.valueOf("2010-01-01"));
		StockInBestand bestand1 = createStockInBestand(deliveryOnDate, "710000", 1000);
		StockInBestand bestand2 = createStockInBestand(deliveryOnDate, "750000", 2000);

		Depot depot2 = createDepot(customer, "depot-2");
		Delivery deliveryBeforeDate = createDelivery(depot2, java.sql.Date.valueOf("2009-01-01"));
		StockInBestand bestand3 = createStockInBestand(deliveryBeforeDate, "710000", 5000);
		StockInBestand bestand4 = createStockInBestand(deliveryBeforeDate, "750000", 8000);
		
		persist(Arrays.asList(bank, customer, depot1, depot2, deliveryOnDate, deliveryBeforeDate, bestand1, bestand2, bestand3, bestand4));
		
		Map<String, Double> valueByDepotNumber = repository.loadValueOfDepotsForBank(bank.getId(), java.sql.Date.valueOf("2010-01-01"));
		HashMap<String, Double> expected = new HashMap<String, Double>();
		expected.put(depot1.getAccountNumber(), 3000.0);
		expected.put(depot2.getAccountNumber(), 13000.0);
		
		Assert.assertEquals(expected, valueByDepotNumber);
	}
	
	/**
	 * Load all depots with bestand value for a particular date for a particular bank. Returns only depots belonging to the correct bank.
	 */
	@Test
	public void testLoadValueOfDepotsForBank_OnlyFindsForCorrectBank() {
		Bank bank = new Bank();
		Bank anotherBank = new Bank();
		Customer customer = createCustomer(bank);
		Customer customerForAnotherBank = createCustomer(anotherBank);
		Depot depot1 = createDepot(customer, "depot-1");
		Delivery deliveryOnDate = createDelivery(depot1, java.sql.Date.valueOf("2010-01-01"));
		StockInBestand bestand1 = createStockInBestand(deliveryOnDate, "710000", 1000);
		StockInBestand bestand2 = createStockInBestand(deliveryOnDate, "750000", 2000);

		Depot depotForAnotherbank = createDepot(customerForAnotherBank, "depot-2");
		Delivery deliveryBeforeDate = createDelivery(depotForAnotherbank, java.sql.Date.valueOf("2009-01-01"));
		StockInBestand bestand3 = createStockInBestand(deliveryBeforeDate, "710000", 5000);
		StockInBestand bestand4 = createStockInBestand(deliveryBeforeDate, "750000", 8000);
		
		persist(Arrays.asList(bank, anotherBank, customer, customerForAnotherBank, depot1, depotForAnotherbank, deliveryOnDate, deliveryBeforeDate, bestand1, bestand2, bestand3, bestand4));
		
		Map<String, Double> valueByDepotNumber = repository.loadValueOfDepotsForBank(bank.getId(), java.sql.Date.valueOf("2011-01-01"));
		HashMap<String, Double> expected = new HashMap<String, Double>();
		expected.put(depot1.getAccountNumber(), 3000.0);
		
		Assert.assertEquals(expected, valueByDepotNumber);
	}
	
	/**
	 * Load all depots with bestand value for a particular date for a particular bank. Depot has multiple delivieries before the occurring before the maximum delivery date, but only the most recent one is relevant
	 */
	@Test
	public void testLoadValueOfDepotsForBank_OnlyFindsMostRecentDeliveryWhenDepotHasMultiple() {
		Bank bank = new Bank();
		Customer customer = createCustomer(bank);
		Depot depot = createDepot(customer, "depot-1");
		Delivery deliveryOnDate = createDelivery(depot, java.sql.Date.valueOf("2010-01-01"));
		StockInBestand bestand1 = createStockInBestand(deliveryOnDate, "710000", 1000);
		StockInBestand bestand2 = createStockInBestand(deliveryOnDate, "750000", 2000);

		Delivery deliveryBeforeDate = createDelivery(depot, java.sql.Date.valueOf("2009-01-01"));
		StockInBestand bestand3 = createStockInBestand(deliveryBeforeDate, "710000", 5000);
		StockInBestand bestand4 = createStockInBestand(deliveryBeforeDate, "750000", 8000);
		
		persist(Arrays.asList(bank, customer, depot, deliveryOnDate, deliveryBeforeDate, bestand1, bestand2, bestand3, bestand4));
		
		Map<String, Double> valueByDepotNumber = repository.loadValueOfDepotsForBank(bank.getId(), java.sql.Date.valueOf("2011-01-01"));
		HashMap<String, Double> expected = new HashMap<String, Double>();
		expected.put(depot.getAccountNumber(), 3000.0);
		
		Assert.assertEquals(expected, valueByDepotNumber);
	}

	private StockInBestand createStockInBestand(Delivery delivery,
			String stockIdentifier, double value) {
		StockInBestand bestand = new StockInBestand();
		
		bestand.setDelivery(delivery);
		bestand.setStockIdentifier(stockIdentifier);
		bestand.setValueInEuro(value);
		
		return bestand;
	}

	private Delivery createDelivery(Depot depot, Date date) {
		Delivery delivery = new Delivery();
		
		delivery.setDepot(depot);
		delivery.setDate(date);
		
		return delivery;
	}

	private Depot createDepot(Customer customer, String depotNumber) {
		Depot depot = new Depot();
		
		depot.setAccountNumber(depotNumber);
		depot.setCustomer(customer);
		
		return depot;
	}

	private <T extends EntitySuperclass> void persist(Iterable<T> entities) {
		for (T entity : entities) {
			session.persist(entity);
		}
		session.flush();
	}

	private Customer createCustomer(Bank bank) {
		Customer customer = new Customer();
		customer.setBank(bank);
		return customer;
	}

}
