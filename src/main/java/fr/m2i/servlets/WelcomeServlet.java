package fr.m2i.servlets;

import java.io.IOException;
import java.util.Map;
import java.util.TreeMap;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import fr.m2i.dbInteractions.TopicUtils;
import fr.m2i.services.AnswerService;
import fr.m2i.services.AuthenticationService;
import fr.m2i.services.TopicsService;

/**
 * Servlet implementation class WelcomeServlet
 */
@WebServlet("/welcome/*")
public class WelcomeServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	private static final String PAGE = "/WEB-INF/pages/index.jsp";
	private static final Map<String, String> PAGES;
	private static final Map<String, String> TITLES;

	static {
		PAGES = new TreeMap<String, String>();
		PAGES.put("/welcome/home", "home.jsp");
		PAGES.put("/welcome/admin", "admin.jsp");
		PAGES.put("/welcome/auth/register", "register.jsp");
		PAGES.put("/welcome/auth/login", "login.jsp");
		PAGES.put("/welcome/auth/logout", "login.jsp");
		PAGES.put("/welcome/thread/id/", "thread.jsp");
		PAGES.put("/welcome/thread/new", "newTopic.jsp");
		PAGES.put("/welcome/category/", "home.jsp");

		TITLES = new TreeMap<String, String>();
		TITLES.put("/welcome/home", "Home page");
		TITLES.put("/welcome/auth/login", "Login page");
		TITLES.put("/welcome/auth/register", "Register page");
	}

	public WelcomeServlet() {
		super();
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String requestUri = request.getRequestURI().substring(request.getContextPath().length());

		if ("/welcome/auth/logout".equals(requestUri)) {
			AuthenticationService authService = new AuthenticationService();
			authService.logout(request.getSession());
			response.sendRedirect(request.getContextPath() + "/welcome/home");
			return;
		}

		if (requestUri != null && requestUri.contains("/welcome/thread/id/")) {
			TopicUtils topicUt = new TopicUtils();
			long topicId = Long.valueOf(requestUri.substring(requestUri.lastIndexOf("/") + 1));
			request.setAttribute("topic", topicUt.findTopicById(topicId));
			String viewname = PAGES.get("/welcome/thread/id/");
			String pageTitle = "thread";
			this.selectView(request, response, viewname, pageTitle);
			return;
		}

		if (requestUri != null && !requestUri.isEmpty() && requestUri.contains("/welcome/category/")) {
			TopicUtils topicUt = new TopicUtils();
			String categ = requestUri.substring(requestUri.lastIndexOf("/") + 1);
			request.setAttribute("topics", topicUt.findTopicsByCategory(categ));
			request.setAttribute("tableTitle", categ);
			String viewname = PAGES.get("/welcome/category/");
			this.selectView(request, response, viewname, categ);
			return;
		}

		if ("/welcome/home".equals(requestUri)) {
			TopicUtils topicUt = new TopicUtils();
			request.setAttribute("topics", topicUt.findAllTopics());
			request.setAttribute("tableTitle", "All topics");
		}

		if (requestUri != null && !requestUri.isEmpty() && PAGES.containsKey(requestUri)) {
			String viewname = PAGES.get(requestUri);
			String pageTitle = TITLES.get(requestUri);
			this.selectView(request, response, viewname, pageTitle);
		} else {
			TopicUtils topicUt = new TopicUtils();
			request.setAttribute("topics", topicUt.findAllTopics());
			String viewname = PAGES.get("/welcome/home");
			String pageTitle = TITLES.get("/welcome/home");
			this.selectView(request, response, viewname, pageTitle);
		}
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String requestUri = request.getRequestURI().substring(request.getContextPath().length());

		if ("/welcome/auth/register".equals(requestUri)) {
			AuthenticationService authService = new AuthenticationService();
			String outcome = authService.registerUser(request);
			if (outcome.equals("availability")) {
				request.setAttribute("registerFail", "availability");
				String viewname = PAGES.get("/welcome/auth/register");
				String pageTitle = "ForumApp - Register Fail";
				this.selectView(request, response, viewname, pageTitle);
			} else if (outcome.equals("empty")) {
				request.setAttribute("registerFail", "empties");
				String viewname = PAGES.get("/welcome/auth/register");
				String pageTitle = "ForumApp - Register Fail";
				this.selectView(request, response, viewname, pageTitle);
			} else {
				response.sendRedirect("/welcome/auth/login");
			}
		}

		if ("/welcome/auth/login".equals(requestUri)) {
			AuthenticationService authService = new AuthenticationService();
			HttpSession session = request.getSession();
			String outcome = authService.signIn(request, session);
			if (outcome.equals("empty")) {
				request.setAttribute("loginFail", "empties");
			} else if (outcome.equals("not_found")) {
				request.setAttribute("loginFail", "not_found");
			} else {
				response.sendRedirect(request.getContextPath() + "/welcome/home");
			}
			if (outcome.equals("empty") || outcome.equals("not_found")) {
				String viewname = PAGES.get("/welcome/auth/login");
				String pageTitle = "ForumApp - Log in Failed";
				this.selectView(request, response, viewname, pageTitle);
			}
		}

		if ("/welcome/thread/new".equals(requestUri)) {
			TopicsService topicServ = new TopicsService();
			HttpSession session = request.getSession();
			String outcome = topicServ.addTopic(request, session);
			if (outcome.equals("empty")) {
				request.setAttribute("newTopicFail", "empties");
			} else if (outcome.equals("notLogged")) {
				request.setAttribute("newTopicFail", "notLogged");
			} else {
				response.sendRedirect(request.getContextPath() +"/welcome/home");
			}
			if (outcome.equals("empty") || outcome.equals("notLogged")) {
				String viewname = PAGES.get("/welcome/thread/new");
				String pageTitle = "ForumApp - Failed";
				this.selectView(request, response, viewname, pageTitle);
			}

		}

		if (requestUri != null && requestUri.contains("/welcome/thread/id/")) {
			AnswerService answerServ = new AnswerService();
			HttpSession session = request.getSession();
			String outcome = answerServ.addAnswer(request, session, requestUri);
			if (outcome.equals("empty")) {
				request.setAttribute("newAnswerFail", "empties");
			} else if (outcome.equals("notLogged")) {
				request.setAttribute("newAnswerFail", "notLogged");
			} else {
				response.setHeader("Refresh", "0");
			}
			if (outcome.equals("empty") || outcome.equals("notLogged")) {
				this.doGet(request, response);
			}
		}
	}

	public void selectView(HttpServletRequest request, HttpServletResponse response, String viewName, String title)
			throws ServletException, IOException {
		request.setAttribute("title", title);
		request.setAttribute("viewName", viewName);
		request.getRequestDispatcher(PAGE).forward(request, response);
	}
}