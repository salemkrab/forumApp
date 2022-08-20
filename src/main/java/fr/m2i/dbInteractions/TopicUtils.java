package fr.m2i.dbInteractions;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.NoResultException;
import javax.persistence.Persistence;
import javax.persistence.Query;

import fr.m2i.entities.TopicEnt;
import fr.m2i.entities.UserEnt;

public class TopicUtils {
	private static EntityManagerFactory factory;
	private static EntityManager em;
	private static final Map<String, String> PERSUNITPROPS_MAP;
	
	static {
		PERSUNITPROPS_MAP = new HashMap<>();
		PERSUNITPROPS_MAP.put("javax.persistence.jdbc.url",System.getenv("jawsdb_url"));
		PERSUNITPROPS_MAP.put("javax.persistence.jdbc.user",System.getenv("jawsdb_user"));
		PERSUNITPROPS_MAP.put("javax.persistence.jdbc.password",System.getenv("jawsdb_passwd"));
	}

	@SuppressWarnings("unchecked")
	public List<TopicEnt> findAllTopics() {
		for (String key : PERSUNITPROPS_MAP.keySet()) {
			System.out.println(key + "  :: " + PERSUNITPROPS_MAP.get(key));
		}

		factory = Persistence.createEntityManagerFactory("ForumApp", PERSUNITPROPS_MAP);
		em = factory.createEntityManager();

		List<TopicEnt> topics;
		Query allTopics = em.createNamedQuery("findAllTopics", TopicEnt.class);
		try {
			topics = allTopics.getResultList();
		} catch (NoResultException nre) {
			topics = null;
		} finally {
			em.close();
			factory.close();
		}
		return topics;
	}
	
	public TopicEnt findTopicById(long id) {
		
		factory = Persistence.createEntityManagerFactory("ForumApp", PERSUNITPROPS_MAP);
		em = factory.createEntityManager();
		
		TopicEnt topic = null;
		Query topicById = em.createNamedQuery("findTopicById", TopicEnt.class);
		topicById.setParameter("topicId", id);
		try {
			topic = (TopicEnt) topicById.getSingleResult();
		} catch (NoResultException nre) {
			topic = null;
		} finally {
			em.close();
			factory.close();
		}
		return topic;
	}
	
	@SuppressWarnings("unchecked")
	public List<TopicEnt> findTopicsByCategory(String category) {

		factory = Persistence.createEntityManagerFactory("ForumApp", PERSUNITPROPS_MAP);
		em = factory.createEntityManager();

		List<TopicEnt> topics;
		Query topicByCateg = em.createNamedQuery("findTopicsByCategory", TopicEnt.class);
		topicByCateg.setParameter("category", category);
		try {
			topics = topicByCateg.getResultList();
		} catch (NoResultException nre) {
			topics = null;
		} finally {
			em.close();
			factory.close();
		}
		return topics;
	}

	@SuppressWarnings("unchecked")
	public List<TopicEnt> findTopicsByUser(long userId) {

		factory = Persistence.createEntityManagerFactory("ForumApp", PERSUNITPROPS_MAP);
		em = factory.createEntityManager();

		List<TopicEnt> topics;
		Query topicByUser = em.createNamedQuery("findTopicsByUser", TopicEnt.class);
		topicByUser.setParameter("userId", userId);
		try {
			topics = topicByUser.getResultList();
		} catch (NoResultException nre) {
			topics = null;
		} finally {
			em.close();
			factory.close();
		}
		return topics;
	}

	
	public boolean insertTopic(String title, String content, String category, UserEnt user) {
		factory = Persistence.createEntityManagerFactory("ForumApp", PERSUNITPROPS_MAP);
		em = factory.createEntityManager();
		TopicEnt topic = new TopicEnt(title, content, category, user);
		
		em.getTransaction().begin();
		boolean transact = false;

		try {
			em.persist(topic);
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
	
	public boolean removeTopic(long topicId) {
		factory = Persistence.createEntityManagerFactory("ForumApp",PERSUNITPROPS_MAP);
		em = factory.createEntityManager();
		
		TopicEnt topicToDelete = em.find(TopicEnt.class, topicId);
		
		boolean transact = false;
		
		if (topicToDelete != null) {
			
		
		em.getTransaction().begin();
		try {
			em.remove(topicToDelete);;
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
		}
		return transact;
		
		
	}
	
}
