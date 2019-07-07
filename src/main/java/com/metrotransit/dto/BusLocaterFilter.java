package com.metrotransit.dto;

import lombok.Data;

@Data
public class BusLocaterFilter {

	private final String routeName;
	private final String direction;
	private final String busStop;
	
}
