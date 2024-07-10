package com.evn.web.exceptions;

public class WebException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public WebException() {
		super();
	}

	public WebException(String message) {
		super(message);
	}
}
