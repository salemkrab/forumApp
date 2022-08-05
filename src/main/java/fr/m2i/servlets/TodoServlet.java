package fr.m2i.servlets;

import java.io.IOException;
import java.security.MessageDigest;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import fr.m2i.models.*;

/**
 * Servlet implementation class TodoServlet
 */
@WebServlet("/todo")
public class TodoServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static final String PAGE="/WEB-INF/pages/todo.jsp";
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public TodoServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		if(request.getParameter("liste") != null && request.getParameter("liste").equals("true")) {
			request.setAttribute("todos", this.getTodos());
			request.setAttribute("todosJPQL", this.getTodosWithJpql());
			request.setAttribute("todosProg", this.getTodosWithProg());
			request.setAttribute("todoRes", this.getTodoById(8));
		
		}
		
		
		this.getServletContext().getRequestDispatcher(PAGE).forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		this.addTodo(request.getParameter("nom"), request.getParameter("description"));
		response.sendRedirect("/Jpa/todo?liste=true");
		
		
	}
	
	
	protected void addTodo(String nom, String description) {
		EntityManagerFactory factory = Persistence.createEntityManagerFactory("UnityPersist");
		EntityManager em = factory.createEntityManager();
		
		Todo todo = new Todo();
		todo.setNom(nom);
		todo.setDescription(description);
		boolean t = false;
		em.getTransaction().begin();
		try {
			
			em.persist(todo);

			t = true;
			}
			finally {
			    if(t) {
			        em.getTransaction().commit();
			    }
			    else {
			        em.getTransaction().rollback();
			    }
			}
		em.close();
		
	}
	
	protected Todo getTodoById(int id) {
		EntityManagerFactory factory = Persistence.createEntityManagerFactory("UnityPersist");
		EntityManager em = factory.createEntityManager();
		Todo todos = (Todo)em.createNativeQuery("select * from todo where id = :id",Todo.class)
				.setParameter("id", id)
				.getSingleResult();
		
		em.close();
		return todos;
		
	}
	protected List<Todo> getTodos(){
		EntityManagerFactory factory = Persistence.createEntityManagerFactory("UnityPersist");
		EntityManager em = factory.createEntityManager();
		List<Todo> todos = (List<Todo>)em.createNativeQuery("select * from todo",Todo.class).getResultList();
		
		
		
		em.close();
		return todos;
	
		
	}
	
	protected List<Todo> getTodosWithJpql(){
		EntityManagerFactory factory = Persistence.createEntityManagerFactory("UnityPersist");
		EntityManager em = factory.createEntityManager();
		List<Todo> todos = (List<Todo>)em.createQuery("select var from Todo var",Todo.class).getResultList();
		
		
		//Exemple de NamedQueries
		
		//Todo maTodo = em.createNamedQuery("selectById", Todo.class)
		//.setParameter("id", 1)
		//.getSingleResult();
		
		
		//Les relations
		//Commande commande = em.find(Commande.class, 1); //em.createQuery("select c from Commande commande where c.client.id = 1",Commande.class).getSingleResult();
		//Commande commande2 = em.createQuery("select c from Commande c where c.client.id = 1", Commande.class).getSingleResult();
	
		Client client = em.find(Client.class, 1);
		List<Commande> commande = em.createQuery("select c from Commande c join c.client client where client.id = 1", Commande.class).getResultList();
		
		System.out.print(client.getCommandes().get(0).getNom());
		
		em.close();
		return todos;
	
	
	}
	
	
	protected List<Todo> getTodosWithProg(){
		EntityManagerFactory factory = Persistence.createEntityManagerFactory("UnityPersist");
		EntityManager em = factory.createEntityManager();
		
	
		
		CriteriaBuilder builder = em.getCriteriaBuilder();
		CriteriaQuery<Todo> query = builder.createQuery(Todo.class);
		
		
		Root<Todo> td = query.from(Todo.class);
	
		query.select(td);
		query.where(builder.equal(td.get("id").as(int.class), 8));
		
		
		
		List<Todo> todos = em.createQuery(query).getResultList();
		
		em.close();
		return todos;
	
		
	}

}
