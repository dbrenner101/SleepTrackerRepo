package com.brenner.sleeptracker.api;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Exception thrown during Account creation when the requested username already exists
 *
 */
@ResponseStatus(HttpStatus.NOT_ACCEPTABLE)// HTTP 406
public class DuplicateUsernameException extends RuntimeException {

	private static final long serialVersionUID = -2764256281368985376L;

	public DuplicateUsernameException() {
		super();
	}

	public DuplicateUsernameException(String message) {
		super(message);
	}

	public DuplicateUsernameException(Throwable cause) {
		super(cause);
	}

	public DuplicateUsernameException(String message, Throwable cause) {
		super(message, cause);
	}

	public DuplicateUsernameException(String message, Throwable cause, boolean enableSuppression,
			boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

}
