package fr.m2i.services;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import fr.m2i.dbInteractions.UserAccountUtils;
import fr.m2i.entities.UserEnt;


public class AuthenticationService {
	UserAccountUtils usertools;

	public AuthenticationService() {
		usertools = new UserAccountUtils();
	}
	
	public String registerUser(HttpServletRequest request) {
		String username = request.getParameter("username");
		String password= request.getParameter("password");
		if (username.isEmpty() || password.isEmpty()) {
			return "empty";
		}
		else {
			boolean success = usertools.registerUser(username, password);
			if (success) {
				return "ok";
			}
			return "availability";
		}

	}

	public String signIn(HttpServletRequest request, HttpSession session) {
		String username = request.getParameter("username");
		String password= request.getParameter("password");
		if (username.isEmpty() || password.isEmpty()) {
			return "empty";
		}
		UserEnt user = usertools.findByUsernamePassword(username, password);

		if (user == null) {
			return "not_found";
		}
		else {
			session.setAttribute("userId", user.getId());
			return "ok";
		}
	}
	
	public void logout(HttpSession session) {
		session.removeAttribute("userId");
	}
	
	public boolean isLogged(HttpSession session) {
		return (session.getAttribute("userId") != null);
	}
}
