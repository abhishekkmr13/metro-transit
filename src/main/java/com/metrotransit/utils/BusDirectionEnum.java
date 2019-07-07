package com.metrotransit.utils;

public enum BusDirectionEnum {

	TEXT("Text"),VALUE("Value");
	
	private String key;

	BusDirectionEnum(String key) {
		this.key = key;
	}

	public String getKey() {
		return key;
	}
}
