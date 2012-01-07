package cse405.message;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Iterator;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@SuppressWarnings("serial")
public class GetMessageListServlet extends HttpServlet {

	private void writeMessage(PrintWriter writer, Message message) {
		writer.print("{ '");
		writer.print(message.getNickname());
		writer.print("', '");
		writer.print(message.getText());
		writer.print("' }");
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		resp.setContentType("text/json");
		PrintWriter writer = resp.getWriter();

		List<Message> messageList = Message.getAll();
		Iterator<Message> messageIter = messageList.iterator();

		writer.println("[");

		if (messageIter.hasNext()) {
			writeMessage(writer, messageIter.next());
		}
		while (messageIter.hasNext()) {
			writer.print(", ");
			writeMessage(writer, messageIter.next());
		}
		writer.println("]");
	}
}
