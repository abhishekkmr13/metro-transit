package com.metrotransit.services;

import java.text.MessageFormat;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.MessageSource;
import org.springframework.context.NoSuchMessageException;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.google.gson.Gson;
import com.jayway.jsonpath.JsonPath;
import com.metrotransit.exception.MetroTrasitBussinessException;
import com.metrotransit.utils.ErrorMsgEnum;
import com.metrotransit.utils.MetrotransitConstants;

@Service
public class MetroTransitBusDeparturesService implements BusDeparturesService {
	
	@Value("${com.metrotransit.url.timepoint-departure}")
	private String TIMEPOINT_DEPARTURE_URL;
	
	@Autowired
	private BusStopService busStopService;
	
	@Autowired
	private BusRouteService busRouteService;
	
	@Autowired
	private BusDirectionService busDirectionService;
	
	@Autowired
	MessageSource messageSource;
	
	
	private String findDepartureDetailsOfAllBuses(String routeId, String directionId, String stopCode) {
		final String uri = MessageFormat.format(TIMEPOINT_DEPARTURE_URL, routeId, directionId, stopCode)+MetrotransitConstants.JSON_FORMAT;

		HttpHeaders headers = new HttpHeaders();
	    headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
		
	    RestTemplate restTemplate = new RestTemplate();
	    HttpEntity<String> entity = new HttpEntity<String>("parameters", headers);
	     
	    ResponseEntity<String> response = restTemplate.exchange(uri, HttpMethod.GET, entity, String.class);
	    
	    return response.getBody();
	}
	
	@Override
	public String findDepartureDetailsForNearestBus(String routeName, String direction, String stopName) throws MetroTrasitBussinessException {

		String routeId = busRouteService.findRouteId(routeName);
		String directionId = busDirectionService.findDirectionIdForRoute(routeId, direction);
		String busStopCode = busStopService.findBusStopCodeForRoute(routeId, directionId, stopName);
		
		List<Map<String, Object>> response = JsonPath.parse(findDepartureDetailsOfAllBuses(routeId, directionId, busStopCode))
				.read("$");
		
		if(null == response || response.size()==0)
			throw new MetroTrasitBussinessException(messageSource.getMessage(
					ErrorMsgEnum.NO_RECORD_FOUND.getValue(), new Object [] {routeName,direction,stopName}, Locale.ENGLISH));
		
		Gson gsonObj = new Gson();
		String nearestDepartureDetails = gsonObj.toJson(response.get(0));
		return nearestDepartureDetails;
	}
	
	@Override
	public String findDepartureDetailsOfNearestBuses(String routeName, String direction, String stopName) throws MetroTrasitBussinessException {

		String routeId = busRouteService.findRouteId(routeName);
		String directionId = busDirectionService.findDirectionIdForRoute(routeId, direction);
		String busStopCode = busStopService.findBusStopCodeForRoute(routeId, directionId, stopName);
		
		return findDepartureDetailsOfAllBuses(routeId, directionId, busStopCode);
	}
	
	@Override
	public String findDepartureDetailsForNearestBus(String allBusesDepDetails) throws MetroTrasitBussinessException, NoSuchMessageException {

		
		List<Map<String, Object>> response = JsonPath.parse(allBusesDepDetails)
				.read("$");
		
		if(null == response || response.size()==0)
			throw new MetroTrasitBussinessException(messageSource.getMessage(
					ErrorMsgEnum.NO_BUS_FOUND.getValue(), null, Locale.ENGLISH));
		
		Gson gsonObj = new Gson();
		String nearestDepartureDetails = gsonObj.toJson(response.get(0));
		return nearestDepartureDetails;
	}
	
	@Override
	public String findDepartureTimeOfNearestBus(String routeName, String direction, String stopName) throws MetroTrasitBussinessException {
		
		String nearestDepartureDetails = findDepartureDetailsForNearestBus(routeName, direction, stopName);
		String departureTime = JsonPath.read(nearestDepartureDetails, "$.DepartureText");
		return departureTime;

	}
	
}
