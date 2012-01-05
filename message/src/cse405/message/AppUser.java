package cse405.message;

import java.security.SecureRandom;
import java.util.ConcurrentModificationException;

import com.google.appengine.api.datastore.DatastoreFailureException;
import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.KeyFactory;
import com.google.appengine.api.datastore.Query;

public class AppUser {
	private static SecureRandom random = new SecureRandom();

	private static final String entityKind = "AppUser";
	private static final String csrfTokenFieldName = "csrfToken";
	private static final String messageFieldName = "message";
	private static final String initialMessageFieldValue = "Hello, World.";

	private Entity appUserEntity;
	private boolean dirty = false;
	
	static {
		// Create the user collection object if it doesn't exist.
		DatastoreService datastore = DatastoreServiceFactory
				.getDatastoreService();
		Key appUserKey = KeyFactory.createKey(AppUser.entityKind, userId);
		Query query = new Query(AppUser.entityKind, appUserKey);
		appUser.appUserEntity = datastore.prepare(query).asSingleEntity();
		if (appUser.appUserEntity == null) {
			appUser.appUserEntity = new Entity(AppUser.entityKind, appUserKey);
			appUser.appUserEntity.setProperty(AppUser.messageFieldName,
					initialMessageFieldValue);
			appUser.createCsrfToken();
		} else if (!appUser.appUserEntity.hasProperty(csrfTokenFieldName)) {
			appUser.createCsrfToken();
		}
		return appUser;
		
	}

	public boolean isDirty() {
		return dirty;
	}

	private AppUser() {
	}

	// This method will set the dirty flag when it creates a new AppUser and
	// when it creates a missing csrf token. If you expect cases, then you
	// should call save().
//	public static AppUser getAuthenticatedAppUser() {
//		UserService userService = UserServiceFactory.getUserService();
//		User user = userService.getCurrentUser();
//		if (user == null) {
//			return null;
//		}
//		return AppUser.findOrCreate(user.getUserId());
//	}

	public static AppUser findOrCreate(String userId) {
		AppUser appUser = new AppUser();
		DatastoreService datastore = DatastoreServiceFactory
				.getDatastoreService();
		Key appUserKey = KeyFactory.createKey(AppUser.entityKind, userId);
		Query query = new Query(AppUser.entityKind, appUserKey);
		appUser.appUserEntity = datastore.prepare(query).asSingleEntity();
		if (appUser.appUserEntity == null) {
			appUser.appUserEntity = new Entity(AppUser.entityKind, appUserKey);
			appUser.appUserEntity.setProperty(AppUser.messageFieldName,
					initialMessageFieldValue);
			appUser.createCsrfToken();
		} else if (!appUser.appUserEntity.hasProperty(csrfTokenFieldName)) {
			appUser.createCsrfToken();
		}
		return appUser;
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

	public void removeCsrfToken() {
		appUserEntity.removeProperty(csrfTokenFieldName);
		dirty = true;
	}

	private void createCsrfToken() {
		String csrfToken = "" + random.nextLong();
		appUserEntity.setProperty(AppUser.csrfTokenFieldName, csrfToken);
		dirty = true;
	}

	public String getCsrfToken() {
		return (String) appUserEntity.getProperty(csrfTokenFieldName);
	}

	public String getMessage() {
		return (String) appUserEntity.getProperty(messageFieldName);
	}

	public void setMessage(String message) {
		appUserEntity.setProperty(messageFieldName, message);
		dirty = true;
	}
}
