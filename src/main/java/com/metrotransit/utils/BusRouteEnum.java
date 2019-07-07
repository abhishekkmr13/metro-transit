package com.metrotransit.utils;

public enum BusRouteEnum {
	
	DESC("Description"),PROVIDER_ID("ProviderID"),ROUTE_ID("Route");
	
	private String key;

	BusRouteEnum(String key) {
		this.key = key;
	}

	public String getKey() {
		return key;
	}
}
