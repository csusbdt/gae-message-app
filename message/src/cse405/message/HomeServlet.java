package cse405.message;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.appengine.api.users.User;

@SuppressWarnings("serial")
public class HomeServlet extends HttpServlet {
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		User user = (User) req.getAttribute("user");
		if (Message.getByUserId(user.getUserId()) == null) {
			req.setAttribute("userMessageExists", "false");
		} else {
			req.setAttribute("userMessageExists", "true");
		}
		RequestDispatcher jsp = req.getRequestDispatcher("/WEB-INF/jsp/home.jsp");
		jsp.forward(req, resp);
	}
}
