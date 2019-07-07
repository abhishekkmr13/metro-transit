package com.metrotransit.exception;

public class MetroTransitTechnicalException extends BaseException {

	private static final long serialVersionUID = 783645639002352L;

	public MetroTransitTechnicalException(String message) {
		super(message);
	}

	public MetroTransitTechnicalException(String message, Throwable t) {
		super(message);
	}

}
