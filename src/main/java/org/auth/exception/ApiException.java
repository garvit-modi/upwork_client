package org.auth.exception;

import jakarta.ws.rs.core.Response;
import lombok.Getter;
import lombok.Setter;

/**
 * Api Exception
 * @author Garvit
 *
 */
@Setter
@Getter
public class ApiException  extends RuntimeException {
	private Response.Status status ;
	private String message;
	/**
	 * @param status
	 * @param message
	 */
	public ApiException(Response.Status status, String message) {
		super(message);
		this.status = status;
		this.message = message;
	}
	/**
	 * @param message
	 * @param status
	 * @param message2
	 */
	public ApiException(String message, Response.Status status, String message2) {
		super(message);
		this.status = status;
		message = message2;
	}
}