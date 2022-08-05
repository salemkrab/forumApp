package fr.m2i.servlets;

import java.io.IOException;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import fr.m2i.models.*;

import fr.m2i.models.Actor;

/**
 * Servlet implementation class WelcomeServlet
 */
@WebServlet("/welcome")
public class WelcomeServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	private static final String PAGE="/WEB-INF/pages/welcome.jsp";
	
	
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public WelcomeServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		request.setAttribute("actor", this.jpaExemple());
		
		
		this.getServletContext().getRequestDispatcher(PAGE).forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}
	
	protected Client jpaExemple() {
		EntityManagerFactory factory = Persistence.createEntityManagerFactory("UnityPersist");
		//Attention pas d'autoclosable
		EntityManager em = factory.createEntityManager();
		
		
		/*
		    find
			persist
			merge
			detach
			refresh
			remove
		 */
	
		Client client = new Client();
		
		
		
		
		
		em.getTransaction().begin();
		boolean transac = false;
		
		try {
			client.setNom("Truc");
			em.persist(client);

			transac = true;
			}
			finally {
			    if(transac) {
			        em.getTransaction().commit();
			    }
			    else {
			        em.getTransaction().rollback();
			    }
			}
		
		em.refresh(client);
		
		
		/*Client clients = (Client)em.createNativeQuery("select * from client where nom like ?")
				.setParameter(1, "%valeur%")
				.getSingleResult();
		*/
		//em.persist(nouvel actor)
		//em.remove(un actor)
		
		//em.detach(entity)
		//em.flush() ---- Attention
		
		//em.flush();
	
		
		/*------
		 * Request Native
		 * <list> = em.createNativeQuery("select --- ?").setParameter(position, valeur).getResultList();
		 *.getSingleResult()
		 *.setMaxResults(....)
		 */
		
		
		
		/*------
		 * JPQL
		 * <list> = em.createQuery("select a from Model a", Model.class).getResultList()
		 * em.createQuery("select agregation(a) from Model a", Model.class).getSingleResult() 
		 * 
		 * .executeUpdate() ---> exemple pour la supp / update d'un element
		 * 
		 * 
		 * 
		 * em.createQuery(".............. where a.attribut = :parametre", Model.class)
		 * .setParameter("parametre",valeur)
		 * .getResultList()
		 */
		
		
		/*-------
		 * Programmatique
		 * 
		 * CriterBuilder builder = em.getCriterialBuilder();
		 * CriterialQuery<Model> query = builder.createQuery(Model.class);
		 * Root<Model> a = query.from(Model.class);
		 * query.select(a);
		 * 
		 * query.where(builder.lessThanOrEqualTo(a.get("attribut").as(type.class), variable);
		 * 
		 * 
		 * <list> = em.createQuery(query).getResultList(); // ou .getSingleResult()
		 * 
		 * 
		 *Les requetes nommées
		 * createNamedQuery("nom")
		 * .setParameter("nom","valuer")
		 * .executeUpdate()
		 */
	
		//em.flush();
		//em.close();
		
		return client;
	}

}
