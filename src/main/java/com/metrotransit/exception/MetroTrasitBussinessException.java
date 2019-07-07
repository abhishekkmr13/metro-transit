package com.metrotransit.exception;

public class MetroTrasitBussinessException extends BaseException {
	
	private static final long serialVersionUID = 7845237181439002352L;

	public MetroTrasitBussinessException(String message) {
		super(message);
	}

	public MetroTrasitBussinessException(String message, Throwable t) {
		super(message);
	}

	
}
