package de.dser.bewerber.be.aop;

import de.dser.bewerber.be.aop.entities.Person;
import de.dser.bewerber.be.aop.exception.DatabaseOperationFailedException;
import de.dser.bewerber.be.aop.exception.NotFoundException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.assertEquals;

/**
 * <p>Implement all tests.</p>
 * <p>In order to verify/load results in a separate transaction, use the {@link AopRepositoryTest#loadAllPeople()} method. This method manages the database connection manually, using no interceptors</p>
 */
public class AopRepositoryTest {

	private SessionFactory sessionFactory;
	private AopRepository repository;

	@Before
	public void setUp() {
		Configuration cfg = new Configuration()
				.setProperty("hibernate.connection.driver_class", "org.hsqldb.jdbcDriver")
				.setProperty("hibernate.hbm2ddl.auto", "create")
				.setProperty("hibernate.dialect", "org.hibernate.dialect.HSQLDialect")
				.setProperty("hibernate.connection.url", "jdbc:hsqldb:mem:aop")
				.setProperty("hibernate.connection.username", "sa")
				.setProperty("hibernate.connection.password", "")
				.setProperty("hibernate.archive.autodetection", "class")
				.addAnnotatedClass(Person.class)
				.setProperty("hibernate.show_sql", "true");
		this.sessionFactory = cfg.buildSessionFactory();
		this.repository = new AopRepository(sessionFactory);
	}

	@After
	public void tearDown() {
		if (sessionFactory != null) {
			sessionFactory.close();
		}
	}

	/**
	 * Create a new Person. Persist it with {@link AopRepository#persistNew(Person)}. Then, use only {@link AopRepositoryTest#loadAllPeople()} to prove that the person has been persisted.
	 * <p>
	 * Tests the method "persistNew". Is successful.
	 * </p>
	 */
	@Test
	public void testPersistNew() {
		Person person = new Person();
		person.setAge(20);
		repository.persistNew(person);
		assertEquals(1, loadAllPeople().size());
	}

	/**
	 * Persist a new Person within the test. Then load it in a separate transaction.
	 * <p>
	 * Tests the loadById method. Is successful.
	 * </p>
	 */
	@Test
	public void testLoadById() throws NotFoundException {
		Person person = new Person();
		person.setAge(20);
		repository.persistNew(person);

		Person loadedPerson = repository.loadById(person.getId());
		assertEquals(20, loadedPerson.getAge());
	}

	/**
	 * Persist a new Person within the test. Then request to load a person with an ID (foreign key) that is not present in the DB.
	 * <p>
	 * Tests the "loadById" method. Person does not exist.
	 * </p>
	 */
	@Test(expected = NotFoundException.class)
	public void testLoadById_PersonDoesNotExist() throws NotFoundException {
		Person person = new Person();
		person.setAge(20);
		repository.persistNew(person);
		repository.loadById(-1);

	}

	/**
	 * Persist a new Person within the test. Then create a detached Entity with a different Age. Update the value in the database using the update method, and then verify
	 * that the age has been updated by re-loading the entity in a separate transaction
	 * <p>
	 * Tests the "update" method. Is successful.
	 * </p>
	 */
	@Test
	public void testUpdate() throws Exception {
		Person person = new Person();
		person.setAge(20);
		repository.persistNew(person);

		person.setAge(30);
		repository.update(person);

		Person persistedPerson = repository.loadById(person.getId());
		assertEquals(30, persistedPerson.getAge());
	}

	/**
	 * Persist a new Person within the test. Then create a detached Entity with a different Age. Update the value in the database using the updateFails method, and then verify
	 * that the age has NOT been updated by re-loading the entity in a separate transaction
	 * <p>
	 * Tests the "updateFails" method. Update fails. Verify that a rollback occurred.
	 * </p>
	 */
	@Test
	public void testUpdateFails() throws Exception {
		Person person = new Person();
		person.setAge(20);
		repository.persistNew(person);

		Person otherPerson = new Person();
		otherPerson.setAge(22);
		try {
			repository.updateFails(otherPerson);
		} catch (RuntimeException e) {
			e.printStackTrace();
		}
		Person originalPerson = repository.loadById(person.getId());
		assertEquals(20, originalPerson.getAge());
	}

	/**
	 * Persist a new Person within the test. Verify within the test that the person has been successfully created in a separate transaction.
	 * Then delete the person. Verify in the test that the person has been deleted using a separate transaction
	 * <p>
	 * Tests the "deleteById" method. Is successful.
	 * </p>
	 */
	@Test(expected = NotFoundException.class)
	public void testDeleteById_DeleteSuccessful() throws NotFoundException {
		Person person = new Person();
		person.setAge(20);
		repository.persistNew(person);
		repository.deleteById(person.getId());
		assertEquals(0, loadAllPeople().size());
		repository.loadById(person.getId());
	}

	/**
	 * Persist 2 Person objects. Attempt to delete a Person with an ID (primary key) that does not exist in the database. In a separate transaction, verify that no
	 * records have been deleted.
	 * <p>
	 * Tests the "deleteById" method, where the given ID does not exist. Verifies that no records have been removed.
	 * </p>
	 */
	@Test
	public void testDeleteById_IdDoesNotExist() {
		Person person1 = new Person();
		person1.setAge(20);
		repository.persistNew(person1);
		Person person2 = new Person();
		person2.setAge(20);
		repository.persistNew(person2);
		try {
			repository.deleteById(-1);
		} catch (NotFoundException e) {
			e.printStackTrace();
		}
		assertEquals(2, loadAllPeople().size());
	}

	/**
	 * Persist a new Person within the test. Verify within the test that the person has been successfully created in a separate transaction.
	 * Then delete the person. Verify in the test that the person has NOT been deleted
	 * <p>
	 * Tests the "deleteByIdFails" method. Attempts to delete a Person that exists, but fails. Subsequently verifies that a rollback occurred.
	 * </p>
	 */
	@Test
	public void testDeleteByIdFails() throws NotFoundException {
		Person person = new Person();
		person.setAge(20);
		repository.persistNew(person);

		assertEquals(1, loadAllPeople().size());
		try {
			repository.deleteByIdFails(person.getId());
		} catch (RuntimeException e) {
			e.printStackTrace();
		}
		assertEquals(1, loadAllPeople().size());
	}

	/**
	 * Loads all Person objects in the database.<br />
	 * Has separate, explicit transaction management to ensure that we are reading/loading the data in a separate transaction
	 *
	 * @return all Person objects
	 */
	@SuppressWarnings("unchecked")
	public List<Person> loadAllPeople() {
		Session session = sessionFactory.openSession();
		try {
			return session.createCriteria(Person.class).list();
		} finally {
			session.close();
		}
	}

}
