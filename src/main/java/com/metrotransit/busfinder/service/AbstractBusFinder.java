package com.metrotransit.busfinder.service;

import com.metrotransit.dto.BusLocaterFilter;
import com.metrotransit.exception.MetroTrasitBussinessException;

public abstract class AbstractBusFinder<V,T> implements BusFinder<T, BusLocaterFilter> {

	public abstract V findDepDetailsOfAllBuses(BusLocaterFilter filter) throws MetroTrasitBussinessException;
	
	public abstract T findBusStopsInDirection(V detailsOfAllBuses) throws MetroTrasitBussinessException;
	
	@Override
	public T findNextBus(BusLocaterFilter filter) throws MetroTrasitBussinessException{
		V departureDetailsOfAllBuses = findDepDetailsOfAllBuses(filter);
		T nearestBus = findBusStopsInDirection(departureDetailsOfAllBuses);
		return nearestBus;
	}
}
