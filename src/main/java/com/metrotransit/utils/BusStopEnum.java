package com.metrotransit.utils;

public enum BusStopEnum {
	TEXT("Text"),VALUE("Value");
	
	private String key;

	BusStopEnum(String key) {
		this.key = key;
	}

	public String getKey() {
		return key;
	}
}
