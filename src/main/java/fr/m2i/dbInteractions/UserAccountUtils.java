package fr.m2i.dbInteractions;

import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.NoResultException;
import javax.persistence.Persistence;
import javax.persistence.Query;

import org.apache.commons.codec.digest.DigestUtils;

import fr.m2i.entities.AnswerEnt;
import fr.m2i.entities.TopicEnt;
import fr.m2i.entities.UserEnt;

public class UserAccountUtils {
	private static EntityManagerFactory factory;
	private static EntityManager em;
	private static final Map<String, String> PERSUNITPROPS_MAP;
	
	static {
		PERSUNITPROPS_MAP = new HashMap<>();
		PERSUNITPROPS_MAP.put("javax.persistence.jdbc.url",System.getenv("jawsdb_url"));
		PERSUNITPROPS_MAP.put("javax.persistence.jdbc.user",System.getenv("jawsdb_user"));
		PERSUNITPROPS_MAP.put("javax.persistence.jdbc.password",System.getenv("jawsdb_passwd"));
	}
	
	public UserEnt findById(long id) {
		factory = Persistence.createEntityManagerFactory("ForumApp", PERSUNITPROPS_MAP);
		em = factory.createEntityManager();
		return em.find(UserEnt.class, id);
	}
	
	public UserEnt findByUsername(String username) {
		
		for (String key : PERSUNITPROPS_MAP.keySet()) {
			System.out.println();
		}
		if (username == null)
			return null;
		
		factory = Persistence.createEntityManagerFactory("ForumApp", PERSUNITPROPS_MAP);
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
		
		factory = Persistence.createEntityManagerFactory("ForumApp", PERSUNITPROPS_MAP);
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
		factory = Persistence.createEntityManagerFactory("ForumApp", PERSUNITPROPS_MAP);
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
	
	public Map<String, Calendar> findUserDates(long userId) {
		factory = Persistence.createEntityManagerFactory("ForumApp",PERSUNITPROPS_MAP);
		em = factory.createEntityManager();
		Map<String, Calendar> userDates = new HashMap<>();
		
		AnswerEnt answ;
		Query userLastAnswerQuery= em.createNamedQuery("findUserAnswersOrderByDateDesc", AnswerEnt.class);
		userLastAnswerQuery.setParameter("userId", userId);
		try {
			answ = (AnswerEnt) userLastAnswerQuery.setMaxResults(1).getSingleResult();
			userDates.put("last-answer", answ.getCreationDate());
		} catch (NoResultException nre) {
			Calendar noDate = Calendar.getInstance();
			noDate.set(1970, 1, 1);
			userDates.put("last-answer", noDate);
		} 
		
		TopicEnt top;
		Query userLastTopicQuery= em.createNamedQuery("findTopicsByUserOrderByDateDesc", TopicEnt.class);
		userLastTopicQuery.setParameter("userId", userId);
		try {
			top = (TopicEnt) userLastTopicQuery.setMaxResults(1).getSingleResult();
			userDates.put("last-topic", top.getCreationDate());
		} catch (NoResultException nre) {
			Calendar noDate = Calendar.getInstance();
			noDate.set(1970, 1, 1);
			userDates.put("last-topic", noDate);
		}
		
		em.close();
		factory.close();
		
		System.out.println(userDates.get("last-topic").getTime());
		System.out.println(userDates.get("last-answer").getTime());
		return userDates;
		
	}
	
}
