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

import com.google.appengine.api.users.User;
import com.google.appengine.api.users.UserService;
import com.google.appengine.api.users.UserServiceFactory;

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
		String csrfToken = Ajax.getCsrfToken(httpReq);		
		if (csrfToken == null) {
			Ajax.sendBadToken(httpResp);
			return;
		}
		String userIdFromToken = CsrfCipher.decryptUserId(csrfToken);
		if (!userIdFromToken.equals(user.getUserId())) {
			Ajax.sendBadToken(httpResp);
		}
		else {
			req.setAttribute("user", user);
			chain.doFilter(req, resp);
		}
	}
}
