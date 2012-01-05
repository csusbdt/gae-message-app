package cse405.message;

import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.KeyFactory;
import com.google.appengine.api.datastore.Query;

public class App {

	private static final String entityKind = "App";

	public static App find() {
		App app = new App();
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

}
