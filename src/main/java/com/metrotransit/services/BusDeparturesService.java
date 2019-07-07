package com.metrotransit.services;

import com.metrotransit.exception.MetroTrasitBussinessException;

public interface BusDeparturesService {

	String findDepartureTimeOfNearestBus(String routeName, String direction, String stopName) throws MetroTrasitBussinessException;

	String findDepartureDetailsForNearestBus(String routeName, String direction, String stopName) throws MetroTrasitBussinessException;

	String findDepartureDetailsForNearestBus(String allBusesDepDetails) throws MetroTrasitBussinessException;

	String findDepartureDetailsOfNearestBuses(String routeName, String direction, String stopName) throws MetroTrasitBussinessException;

}
