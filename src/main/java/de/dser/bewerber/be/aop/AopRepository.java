package de.dser.bewerber.be.aop;

import de.dser.bewerber.be.aop.entities.Person;
import de.dser.bewerber.be.aop.exception.DatabaseOperationFailedException;
import de.dser.bewerber.be.aop.exception.NotFoundException;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import javax.inject.Inject;

public class AopRepository {

	private Session session;
	private Transaction tx;
	private final SessionFactory sessionFactory;

	@Inject
	public AopRepository(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}

	public Person persistNew(Person detachedEntity) {
		Session session = getCurrentSession();
		session.persist(detachedEntity);
		return detachedEntity;
	}

	public Person loadById(long id) throws NotFoundException {
		Person person = getCurrentSession().get(Person.class, id);
		if (person == null) {
			String message = String.format("Could not load Person with ID[%s] because does not exist", id);
			throw new NotFoundException(message);
		}
		return person;
	}


	/**
	 * Don't care about the use-case where the detached entity has a non-existing ID. It will be persisted. This would be a problem in the real world, but
	 * we don't care for this test.
	 */
	public void update(Person detachedEntity) throws NotFoundException {
		getCurrentSession().merge(detachedEntity);
	}

	public void updateFails(Person detachedEntity) {
		Session session = getCurrentSession();
		session.merge(detachedEntity);
		session.flush();
		throw new RuntimeException("Oops!");
	}

	public void deleteById(long id) throws NotFoundException {
		int rowsUpdated = getCurrentSession().createQuery("delete from Person where id = :id").setLong("id", id).executeUpdate();
		if (rowsUpdated != 1) {
			String message = String.format("Could not delete Person with ID[%s] because does not exist", id);
			throw new NotFoundException(message);
		}
	}

	public void deleteByIdFails(long id) throws NotFoundException {
		int rowsUpdated = getCurrentSession().createQuery("delete from Person where id = :id").setLong("id", id).executeUpdate();
		if (rowsUpdated != 1) {
			String message = String.format("Could not delete Person with ID[%s] because does not exist", id);
			throw new NotFoundException(message);
		}
		throw new DatabaseOperationFailedException("Failed, oops!");
	}

	private Session getCurrentSession() {
		if (!session.isOpen()) {
			session = sessionFactory.openSession();
		}
		return session;
	}

	private void openConnection() {
		if (session == null) {
			session = sessionFactory.openSession();
		}
		tx = getCurrentSession().beginTransaction();
	}

	private void rollBackTransaction() {
		try {
			if (tx.isActive()) {
				tx.rollback();
			}
		} finally {
			if (session != null) {
				session.close();
			}
		}
	}

	private void closeConnection() {
		try {
			if (tx.isActive()) {
				tx.commit();
			}
		} finally {
			if (session != null) {
				session.close();
			}
		}
	}

	@Aspect
	public static class Interceptor {

		@Before("execution(public * de.dser.bewerber.be.aop.AopRepository.*(..))")
		public void openConnection(JoinPoint joinPoint) {
			AopRepository repository = (AopRepository) joinPoint.getThis();
			repository.openConnection();
		}

		@AfterThrowing(value = "execution(public * de.dser.bewerber.be.aop.AopRepository.*(..))", throwing = "e")
		public void rollBack(JoinPoint joinPoint, Throwable e) {
			AopRepository repository = (AopRepository) joinPoint.getThis();
			repository.rollBackTransaction();
		}

		@After("execution(public * de.dser.bewerber.be.aop.AopRepository.*(..))")
		public void closeTransaction(JoinPoint joinPoint) {
			AopRepository repository = (AopRepository) joinPoint.getThis();
			repository.closeConnection();
		}
	}


}
