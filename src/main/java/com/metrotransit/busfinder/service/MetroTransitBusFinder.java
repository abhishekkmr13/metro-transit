package com.metrotransit.busfinder.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.metrotransit.dto.BusLocaterFilter;
import com.metrotransit.exception.MetroTrasitBussinessException;
import com.metrotransit.services.BusDeparturesService;


@Component("metroTransitBusFinder")
public class MetroTransitBusFinder extends AbstractBusFinder<String, String> {


	@Autowired
	private BusDeparturesService busDeparturesService;

	@Override
	public String findDepDetailsOfAllBuses(BusLocaterFilter filter) throws MetroTrasitBussinessException {
		String response = busDeparturesService.findDepartureDetailsOfNearestBuses(filter.getRouteName(), filter.getDirection(), filter.getBusStop());
		return response;
	}

	@Override
	public String findBusStopsInDirection(String detailsOfAllBuses) throws MetroTrasitBussinessException {
		
		return busDeparturesService.findDepartureDetailsForNearestBus(detailsOfAllBuses);
	}


}
