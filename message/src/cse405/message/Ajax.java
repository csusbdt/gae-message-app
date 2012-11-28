package cse405.message;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class Ajax {
	
	private static final String NOT_AUTHENTICATED = "NOT_AUTHENTICATED";
	private static final String BAD_CSRF_TOKEN = "BAD_CSRF_TOKEN";
	private static final String ILLEGAL_REQUEST = "ILLEGAL_REQUEST";
	private static final String CONCURRENT_MODIFICATION_EXCEPTION = "CONCURRENT MODIFICATION EXCEPTION";
	private static final String DATASTORE_FAILURE_EXCEPTION = "DATASTORE_FAILURE_EXCEPTION";	
	private static final String CSRF_TOKEN_HEADER = "X-CSRF-Token";
		
	public static void sendBadToken(HttpServletResponse resp)
			throws IOException {
		resp.sendError(300, BAD_CSRF_TOKEN);
	}

	public static void sendNotAuthenticated(HttpServletResponse resp)
			throws IOException {
		resp.sendError(300, NOT_AUTHENTICATED);
	}

	public static void sendIllegalRequest(HttpServletResponse resp)
			throws IOException {
		resp.sendError(300, ILLEGAL_REQUEST);
	}

	public static void sendConcurrentModificationException(HttpServletResponse resp)
			throws IOException {
		resp.sendError(300, CONCURRENT_MODIFICATION_EXCEPTION);
	}

	public static void sendDatastoreFailureException(HttpServletResponse resp)
			throws IOException {
		resp.sendError(300, DATASTORE_FAILURE_EXCEPTION);
	}
	
	public static String getCsrfToken(HttpServletRequest req)
			throws IOException {
		return req.getHeader(CSRF_TOKEN_HEADER);
	}
}
