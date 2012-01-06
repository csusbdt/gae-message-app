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
public class GetUsersServlet extends HttpServlet {
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		resp.setContentType("text/json");
		PrintWriter writer = resp.getWriter();
		writer.println("[");
		List<String> userIdList = AppUser.getUserIdList();
		Iterator<String> userIdIter = userIdList.iterator();
		if (userIdIter.hasNext())
		{
			writer.print("\"");			
			writer.print(userIdIter.next());			
			writer.print("\"");			
		}
		while (userIdIter.hasNext())
		{
			writer.print(", \"");			
			writer.print(userIdIter.next());			
			writer.print("\"");			
		}
		writer.println("]");
	}
}
