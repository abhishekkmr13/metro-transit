package com.metrotransit.utils;

public enum ResponseStatusCode {

	SUCCESS("SUCCESS"),FAILED("FAILED");

	private String code;

	ResponseStatusCode(String code) {
		this.code = code;
	}

	public String getCode() {
		return code;
	}

}
