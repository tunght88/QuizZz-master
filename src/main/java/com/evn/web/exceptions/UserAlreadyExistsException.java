package com.evn.web.exceptions;

public class UserAlreadyExistsException extends WebException {

	private static final long serialVersionUID = 1L;

	public UserAlreadyExistsException() {
		super();
	}

	public UserAlreadyExistsException(String message) {
		super(message);
	}

}
