package com.metrotransit.exception;

public class BaseException extends Exception {


	private static final long serialVersionUID = 4326633159964314109L;

	private String message;

	public BaseException(String message) {
		super(message);
		this.message=message;
	}

	public BaseException(String message, Throwable t) {
		super(message,t);
		this.message=message;
	}

	@Override
	public String getMessage() {
		return message;
	}
	
}
