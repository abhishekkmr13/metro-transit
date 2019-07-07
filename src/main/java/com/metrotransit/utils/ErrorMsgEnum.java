package com.metrotransit.utils;

public enum ErrorMsgEnum {
	
	NO_RECORD_FOUND("no-bus-found"),
	NO_BUS_FOUND("no-bus"),
	INVALID_DIRECTION("invalid-direction"),
	INVALID_ROUTE("invalid-route"),
	INVALID_STOP("invalid-stop");
	
	ErrorMsgEnum(String errorMsgKey) {
		this.errorMsgKey = errorMsgKey;
	}
	
	private String errorMsgKey;
	
	
	public String getValue() {
		return errorMsgKey;
	}
}
