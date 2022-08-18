package fr.m2i.filters;

import java.io.IOException;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import fr.m2i.services.AuthenticationService;

/**
 * Servlet Filter implementation class userProfileFilter
 */
@WebFilter("/welcome/user/*")
public class userProfileFilter extends HttpFilter implements Filter {

	private static final long serialVersionUID = 1L;

    public userProfileFilter() {
        super();

    }


	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
		HttpServletRequest req = (HttpServletRequest) request;
		HttpServletResponse resp = (HttpServletResponse) response;
		
		AuthenticationService authService = new AuthenticationService();
		HttpSession session = req.getSession(false);

		String requestUri = req.getRequestURI().substring(req.getContextPath().length());
		long requestUserId = Long.valueOf(requestUri.substring(requestUri.lastIndexOf("/") + 1));
		Long logguedUserId = authService.logguedUserId(session);
		if (logguedUserId == null) {
			resp.sendRedirect(req.getContextPath() + "/welcome/home");
		}
		else {
			if (!logguedUserId.equals(requestUserId)) {
				resp.sendRedirect(req.getContextPath() + "/welcome/user/"+logguedUserId);
			}
			else {
				chain.doFilter(req, response);
			}
		}
		

	}


}
