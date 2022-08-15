package fr.m2i.services;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import fr.m2i.dbInteractions.AnswerUtils;
import fr.m2i.dbInteractions.TopicUtils;
import fr.m2i.entities.TopicEnt;
import fr.m2i.entities.UserEnt;

public class AnswerService {
	public String addAnswer(HttpServletRequest request, HttpSession session, String requestUri) {
		AuthenticationService authService = new AuthenticationService();
		AnswerUtils answUt;
		UserService userServ;
		TopicUtils topicUtil;
		String content = request.getParameter("AnswerContent");
		long topicId = Long.valueOf(requestUri.substring(requestUri.lastIndexOf("/") + 1));			

		if (!authService.isLogged(session)) {
			return "notLogged";
		} else if (content.isEmpty()) {
			return "empty";
		} else {
			answUt = new AnswerUtils();
			userServ = new UserService();
			topicUtil = new TopicUtils();
			UserEnt user = userServ.findLoggedUser(session);
			TopicEnt topic = topicUtil.findTopicById(topicId);
			answUt.insertAnswer(content, user, topic);
			return "ok";
		}
	}
}
