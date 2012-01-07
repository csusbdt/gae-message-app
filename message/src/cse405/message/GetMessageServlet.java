package cse405.message;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.KeyFactory;
import com.google.appengine.api.datastore.Query;
import com.google.appengine.api.users.User;

@SuppressWarnings("serial")
public class GetMessageServlet extends HttpServlet {
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		User user = (User) req.getAttribute("user");
		DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
		Key key = KeyFactory.createKey("message", user.getUserId());
		Query query = new Query("message", key);
		Entity messageEntity = datastore.prepare(query).asSingleEntity();
		resp.setContentType("text/json");
		PrintWriter writer = resp.getWriter();
		writer.print("{ 'email': '");
		writer.print(messageEntity.getProperty("email"));
		writer.print("', 'text': '");
		if (messageEntity != null) {
			writer.print(messageEntity.getProperty("text"));
		}
		writer.print("' }");
	}
}
