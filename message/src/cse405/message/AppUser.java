package cse405.message;

import static com.google.appengine.api.datastore.FetchOptions.Builder.withLimit;

import java.util.ConcurrentModificationException;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import com.google.appengine.api.datastore.DatastoreFailureException;
import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.KeyFactory;
import com.google.appengine.api.datastore.Query;
import com.google.appengine.api.users.User;

public class AppUser {
	private static final String entityKind = "AppUser";
	private static final String nicknameFieldName = "nickname";
	private static final String messageFieldName = "message";
	private static final String initialMessageFieldValue = "Hello, World.";

	private Entity appUserEntity;
	private boolean dirty = false;
	
	public boolean isDirty() {
		return dirty;
	}

	private AppUser() {
	}

	public static AppUser findOrCreate(User user) {
		AppUser appUser = new AppUser();
		DatastoreService datastore = DatastoreServiceFactory
				.getDatastoreService();
		Key appUserKey = KeyFactory.createKey(AppUser.entityKind, user.getUserId());
		Query query = new Query(AppUser.entityKind, appUserKey);
		appUser.appUserEntity = datastore.prepare(query).asSingleEntity();
		if (appUser.appUserEntity == null) {
			appUser.appUserEntity = new Entity(AppUser.entityKind, appUserKey);
			appUser.appUserEntity.setProperty(AppUser.nicknameFieldName,
					user.getNickname());
			appUser.appUserEntity.setProperty(AppUser.messageFieldName,
					initialMessageFieldValue);
			appUser.dirty = true;
		}
		return appUser;
	}
	
	public static List<String> getUserIdList() {
		LinkedList<String> userIdList = new LinkedList<String>();
		DatastoreService datastore = DatastoreServiceFactory
				.getDatastoreService();
		Query query = new Query(AppUser.entityKind);
		Iterator<Entity> appUserIter = datastore.prepare(query).asIterator(withLimit(100));
		while (appUserIter.hasNext()) {
			Entity entity = appUserIter.next();
			userIdList.add(entity.getAppId());
		}		
		return userIdList;
	}

	public void save() throws ConcurrentModificationException,
			DatastoreFailureException {
		if (dirty) {
			DatastoreService datastore = DatastoreServiceFactory
					.getDatastoreService();
			datastore.put(appUserEntity);
			dirty = false;
		}
	}

	public String getMessage() {
		return (String) appUserEntity.getProperty(messageFieldName);
	}

	public void setMessage(String message) {
		appUserEntity.setProperty(messageFieldName, message);
		dirty = true;
	}
}
