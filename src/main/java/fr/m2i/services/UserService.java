package fr.m2i.services;

import javax.servlet.http.HttpSession;

import fr.m2i.dbInteractions.UserAccountUtils;
import fr.m2i.entities.UserEnt;

public class UserService {
	UserAccountUtils userUtils;

	public UserEnt findLoggedUser(HttpSession session) {
		userUtils = new UserAccountUtils();
		long userIdFromSession = (long) session.getAttribute("userId");
		return userUtils.findById(userIdFromSession);
	}
}
