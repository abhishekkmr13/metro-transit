package com.metrotransit.services;

import com.metrotransit.exception.MetroTrasitBussinessException;

public interface BusRouteService {

	String getRoutes() throws MetroTrasitBussinessException;

	String getRouteFor(String routeName) throws MetroTrasitBussinessException;

	String findRouteId(String routeName) throws MetroTrasitBussinessException;

}
