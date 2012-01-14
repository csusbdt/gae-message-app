package cse405.message;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.appengine.api.users.User;

@SuppressWarnings("serial")
public class DeleteMessageServlet extends HttpServlet {
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		User user = (User) req.getAttribute("user");
		Message message = Message.getByUserId(user.getUserId());
		message.delete();
		GetMessageListServlet.sendMessageList(req, resp);
	}
}
