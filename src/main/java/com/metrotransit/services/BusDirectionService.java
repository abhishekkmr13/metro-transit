package com.metrotransit.services;

import com.metrotransit.exception.MetroTrasitBussinessException;

public interface BusDirectionService {

	String getDirectionsForRoute(String routeId) throws MetroTrasitBussinessException;

	String findDirectionIdForRoute(String routeId, String direction) throws MetroTrasitBussinessException;

}
