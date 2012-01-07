package cse405.message;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.EntityNotFoundException;
import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.KeyFactory;
import com.google.appengine.api.datastore.PreparedQuery.TooManyResultsException;
import com.google.appengine.api.datastore.Query;
import com.google.appengine.api.datastore.Query.FilterOperator;

// The application design is that authenticated users have zero or one messages.
// This class is not thread safe because it is possible to create 2 messages for
// a single user under the right conditions.  This problem can be solved using
// transactions.
public class Message {
	
	private Entity entity = null;

	private Message(Entity entity) {
		this.entity = entity;
	}
	
	public String getText() {
		return (String) entity.getProperty("text");
	}
	
	public String getNickname() {
		return (String) entity.getProperty("nickname");
	}
	
	private void setNickname(String nickname) {
		entity.setProperty("nickname", nickname);
	}
	
	private void setText(String text) {
		entity.setProperty("text", text);
	}
	
	private void save() {
		DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
		datastore.put(entity);		
	}

	public static Message createOrUpdate(String userId, String nickname, String text) {
		Message message = getByUserId(userId);
		if (message != null) {
			message.setNickname(nickname);
			message.setText(text);
		} else {
			Entity entity = new Entity("message");
			entity.setProperty("nickname", nickname);
			entity.setProperty("text", text);
			message = new Message(entity);
		}
		message.save();
		return message;
	}

	public static List<Message> getAll() {
		Query query = new Query("message");
		DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
		Iterator<Entity> entityIter = datastore.prepare(query).asIterator();
		List<Message> messageList = new LinkedList<Message>();
		while (entityIter.hasNext()) {
			messageList.add(new Message(entityIter.next()));
		}
		return messageList;
	}
	
	public static Message getById(String id) {
		Key key = KeyFactory.createKey("message", id);
		DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
		Entity entity;
		try {
			entity = datastore.get(key);
		} catch (EntityNotFoundException e) {
			return null;
		}
		return new Message(entity);
	}

	public static Message getByUserId(String userId) {
		Query query = new Query("message");
		query.addFilter("userId", FilterOperator.EQUAL, userId);
		DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
		Entity entity = null;
		try {
			entity = datastore.prepare(query).asSingleEntity();
		} catch (TooManyResultsException e) {
			e.printStackTrace();
			return null;
		}
		return new Message(entity);
	}
}
