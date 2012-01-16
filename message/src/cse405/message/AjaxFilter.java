package cse405.message;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.google.appengine.api.users.User;
import com.google.appengine.api.users.UserService;
import com.google.appengine.api.users.UserServiceFactory;

/**
 * The approach to CSRF attacks included in this application requires
 * sessions to be turned on.  I believe that sessions may be expensive
 * in GAE apps or require restrictions that reduce scalability.
 * I would like to try the following approach in order to omit sessions:
 * 
 *    http://stackoverflow.com/questions/198520/how-to-best-prevent-csrf-attacks-in-a-gae-app/908348#908348
 *    
 */
public class AjaxFilter implements Filter {

	@Override
	public void init(FilterConfig arg0) throws ServletException {
	}

	@Override
	public void destroy() {
	}

	@Override
	public void doFilter(ServletRequest req, ServletResponse resp,
			FilterChain chain) throws IOException, ServletException {
		HttpServletResponse httpResp = (HttpServletResponse) resp;
		HttpServletRequest httpReq = (HttpServletRequest) req;
		UserService userService = UserServiceFactory.getUserService();
		User user = userService.getCurrentUser();
		if (user == null) {
			Ajax.sendNotAuthenticated(httpResp);
			return;
		}
		HttpSession session = httpReq.getSession();
		String csrfTokenFromRequest = Ajax.getCsrfToken(httpReq);
		String csrfTokenFromSession = (String) session.getAttribute("csrfToken");
		if (    csrfTokenFromSession == null || 
				csrfTokenFromRequest == null || 
				!csrfTokenFromRequest.equals(csrfTokenFromSession)) {
			Ajax.sendBadToken(httpResp);
		}		
		else {
			req.setAttribute("user", user);
			chain.doFilter(req, resp);
		}
	}

}
