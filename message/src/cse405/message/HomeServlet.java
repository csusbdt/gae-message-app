package cse405.message;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@SuppressWarnings("serial")
public class HomeServlet extends HttpServlet {
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		req.setAttribute("errorMessage", "There are no errors.");
		RequestDispatcher jsp = req.getRequestDispatcher("/WEB-INF/jsp/home.jsp");
		jsp.forward(req, resp);
	}
}
