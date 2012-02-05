package cse405.message;

import java.io.IOException;

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
		
		text = text
			.replaceAll("<", "&lt;")
			.replaceAll("&lt;a ", "<a ")
			.replaceAll("&lt;/a>", "</a>")
			.replaceAll("&lt;br>", "<br>")
			.replaceAll("&lt;i>", "<i>")
			.replaceAll("&lt;/i>", "</i>")
			.replaceAll("&lt;b>", "<b>")
			.replaceAll("&lt;/b>", "</b>")
			.replaceAll("&lt;s>", "<s>")
			.replaceAll("&lt;/s>", "</s>");

		Message.createOrUpdate(user.getUserId(), user.getNickname(), text);	
		GetMessageListServlet.sendMessageList(req, resp);
	}
}
