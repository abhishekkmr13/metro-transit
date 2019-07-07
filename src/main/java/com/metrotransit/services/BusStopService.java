package com.metrotransit.services;

import com.metrotransit.exception.MetroTrasitBussinessException;

public interface BusStopService {

	String getBusStopsForRouteInDirection(String routeId, String directionId) throws MetroTrasitBussinessException;

	String findBusStopCodeForRoute(String routeId, String directionId, String busStopName) throws MetroTrasitBussinessException;

}
