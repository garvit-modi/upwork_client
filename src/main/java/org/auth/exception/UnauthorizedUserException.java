package org.auth.exception;

import jakarta.ws.rs.core.Response;
import lombok.Getter;
import lombok.Setter;

/**
 * Unauthorized User Exception
 * @author Garvit
 *
 */
@Setter
@Getter
public class UnauthorizedUserException  extends RuntimeException {
	private Response.Status status = Response.Status.UNAUTHORIZED ;
	private String message;
	/**
	 * @param status
	 * @param message
	 */
	public UnauthorizedUserException( String message) {
		super(message);
		this.message = message;
	}
	/**
	 * @param message
	 * @param status
	 * @param message2
	 */
	public UnauthorizedUserException(String message, Response.Status status, String message2) {
		super(message);
		this.status = status;
		message = message2;
	}
}

