package fr.m2i.dbInteractions;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.NoResultException;
import javax.persistence.Persistence;
import javax.persistence.Query;

import fr.m2i.entities.TopicEnt;
import fr.m2i.entities.UserEnt;

public class TopicUtils {
	EntityManagerFactory factory;
	EntityManager em;


	@SuppressWarnings("unchecked")
	public List<TopicEnt> findAllTopics() {

		factory = Persistence.createEntityManagerFactory("ForumApp");
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
		
		factory = Persistence.createEntityManagerFactory("ForumApp");
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

		factory = Persistence.createEntityManagerFactory("ForumApp");
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

		factory = Persistence.createEntityManagerFactory("ForumApp");
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
		factory = Persistence.createEntityManagerFactory("ForumApp");
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
		factory = Persistence.createEntityManagerFactory("ForumApp");
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
