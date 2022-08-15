package fr.m2i.dbInteractions;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.NoResultException;
import javax.persistence.Persistence;
import javax.persistence.Query;

import fr.m2i.entities.AnswerEnt;
import fr.m2i.entities.TopicEnt;
import fr.m2i.entities.UserEnt;

public class AnswerUtils {
	EntityManagerFactory factory;
	EntityManager em;

	@SuppressWarnings("unchecked")
	public List<AnswerEnt> findAnswersByTopic(long topicId) {

		factory = Persistence.createEntityManagerFactory("zbzbgb566al1ilvj");
		em = factory.createEntityManager();

		List<AnswerEnt> answers;
		Query allAnswersQuery = em.createNamedQuery("findAnswersByTopic", AnswerEnt.class);
		allAnswersQuery.setParameter("topicId", topicId);

		try {
			answers = allAnswersQuery.getResultList();
		} catch (NoResultException nre) {
			answers = null;
		} finally {
			em.close();
			factory.close();
		}
		return answers;
	}

	public AnswerEnt findAnswerById(long id) {

		factory = Persistence.createEntityManagerFactory("zbzbgb566al1ilvj");
		em = factory.createEntityManager();

		AnswerEnt answer = null;
		Query answerById = em.createNamedQuery("findAnswerById", TopicEnt.class);
		answerById.setParameter("answerId", id);
		try {
			answer = (AnswerEnt) answerById.getSingleResult();
		} catch (NoResultException nre) {
			answer = null;
		} finally {
			em.close();
			factory.close();
		}
		return answer;
	}

	@SuppressWarnings("unchecked")
	public List<AnswerEnt> findAnswersByUser(long userId) {

		factory = Persistence.createEntityManagerFactory("zbzbgb566al1ilvj");
		em = factory.createEntityManager();

		List<AnswerEnt> answers;
		Query answersByUserQuery = em.createNamedQuery("findAnswersByTopic", AnswerEnt.class);
		answersByUserQuery.setParameter("userId", userId);

		try {
			answers = answersByUserQuery.getResultList();
		} catch (NoResultException nre) {
			answers = null;
		} finally {
			em.close();
			factory.close();
		}
		return answers;
	}

	public boolean insertAnswer(String content, UserEnt user, TopicEnt topic) {
		factory = Persistence.createEntityManagerFactory("zbzbgb566al1ilvj");
		em = factory.createEntityManager();
		AnswerEnt answer = new AnswerEnt(content, user, topic);

		em.getTransaction().begin();
		boolean transact = false;

		try {
			em.persist(answer);
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
