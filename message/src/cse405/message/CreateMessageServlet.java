package cse405.message;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.appengine.api.users.User;

@SuppressWarnings("serial")
public class CreateMessageServlet extends HttpServlet {
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		String text = req.getParameter("text");
		if (text == null) {
			Ajax.sendIllegalRequest(resp);
			return;
		}
		User user = (User) req.getAttribute("user");
		Message message = Message.createOrUpdate(user.getUserId(), user.getNickname(), text);
		resp.setContentType("text/json");
		PrintWriter writer = resp.getWriter();
		writer.print("{ 'id': '");
		writer.print(message.getID());
		writer.print("', 'nickname': '");
		writer.print(message.getNickname());
		writer.print("' }");
	}
}
