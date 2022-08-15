package fr.m2i.dbInteractions;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.NoResultException;
import javax.persistence.Persistence;
import javax.persistence.Query;

import org.apache.commons.codec.digest.DigestUtils;

import fr.m2i.entities.UserEnt;

public class UserAccountUtils {
	EntityManagerFactory factory;
	EntityManager em;
	
	public UserEnt findById(long id) {
		factory = Persistence.createEntityManagerFactory("zbzbgb566al1ilvj");
		em = factory.createEntityManager();
		return em.find(UserEnt.class, id);
	}
	
	public UserEnt findByUsername(String username) {
		if (username == null)
			return null;
		
		factory = Persistence.createEntityManagerFactory("zbzbgb566al1ilvj");
		em = factory.createEntityManager();
		UserEnt user = null;
		Query userByEmail = em.createNamedQuery("findUserByUsername", UserEnt.class);
		userByEmail.setParameter("username", username);
		try {
			user = (UserEnt) userByEmail.getSingleResult();
		} catch (NoResultException nre) {
			user = null;
		} finally {
			em.close();
			factory.close();
		}
		return user;
		
	}
	
	public UserEnt findByUsernamePassword(String username, String password) {
		if (username == null || password == null)
			return null;
		
		factory = Persistence.createEntityManagerFactory("zbzbgb566al1ilvj");
		em = factory.createEntityManager();
		UserEnt user = null;
		Query userByEmailPasswd = em.createNamedQuery("findUserByUsernamePassword", UserEnt.class);
		userByEmailPasswd.setParameter("username", username)
		.setParameter("password", DigestUtils.sha1Hex(password));
		try {
			user = (UserEnt) userByEmailPasswd.getSingleResult();
		} catch (NoResultException nre) {
			user = null;
		} finally {
			em.close();
			factory.close();
		}
		return user;
		
	}
	
	public boolean registerUser(String username, String passwd) {
		factory = Persistence.createEntityManagerFactory("zbzbgb566al1ilvj");
		em = factory.createEntityManager();
		UserEnt user = new UserEnt(username, DigestUtils.sha1Hex(passwd));
		
		em.getTransaction().begin();
		boolean transact = false;

		try {
			em.persist(user);
			transact = true;
		} catch (RuntimeException e) {
			em.getTransaction().rollback();
			throw e;
		} finally {
			if (transact)
				em.getTransaction().commit();
			else
				em.getTransaction().rollback();

			em.close();
			factory.close();
		}
		return transact;
	}
}
