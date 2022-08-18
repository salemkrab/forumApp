package fr.m2i.filters;

import java.io.IOException;
import java.util.List;

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

import fr.m2i.dbInteractions.TopicUtils;
import fr.m2i.entities.TopicEnt;
import fr.m2i.services.AuthenticationService;

/**
 * Servlet Filter implementation class deleteTopicFilter
 */
@WebFilter("/welcome/thread/delete/id/*")
public class deleteTopicFilter extends HttpFilter implements Filter {

	private static final long serialVersionUID = 1L;

	public deleteTopicFilter() {
		super();
	}

	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		HttpServletRequest req = (HttpServletRequest) request;
		HttpServletResponse resp = (HttpServletResponse) response;
		AuthenticationService authService = new AuthenticationService();
		HttpSession session = req.getSession(false);

		String requestUri = req.getRequestURI().substring(req.getContextPath().length());
		long deleteRequestTopicId = Long.valueOf(requestUri.substring(requestUri.lastIndexOf("/") + 1));
		Long logguedUserId = authService.logguedUserId(session);
		if (logguedUserId == null) {
			resp.sendRedirect(req.getContextPath() + "/welcome/home");
		} else {
			TopicUtils topicUt = new TopicUtils();
			List<TopicEnt> topicList = topicUt.findTopicsByUser(logguedUserId);
			if (topicList != null) {
				boolean isPresent = false;
				for (TopicEnt topic : topicList) {
					if (topic.getId() == deleteRequestTopicId) {
						isPresent = true;
					}
				}

				if (!isPresent) {
					resp.sendRedirect(req.getContextPath() + "/welcome/user/" + logguedUserId);
				} else {
					chain.doFilter(request, response);
				}
			}
		}
	}

	public void init(FilterConfig fConfig) throws ServletException {
	}

}
