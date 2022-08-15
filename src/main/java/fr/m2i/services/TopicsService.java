package fr.m2i.services;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import fr.m2i.dbInteractions.TopicUtils;
import fr.m2i.entities.UserEnt;

public class TopicsService {
	
	public String addTopic(HttpServletRequest request, HttpSession session ) {
		AuthenticationService authService = new AuthenticationService();
		TopicUtils topicUt;
		UserService userServ;
		String title = request.getParameter("topicTitle");
		String content = request.getParameter("TopicContent");
		String category = request.getParameter("category");
		
		if (!authService.isLogged(session)) {
			return "notLogged";
		}
		else if (title.isEmpty() || category.isEmpty()) {
			return "empty";
		} 
		else {
			topicUt = new TopicUtils();
			userServ = new UserService();
			UserEnt user = userServ.findLoggedUser(session);
			topicUt.insertTopic(title, content, category, user);
			return "ok";
		}

	}

}
