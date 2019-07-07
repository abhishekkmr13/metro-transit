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

import com.jayway.jsonpath.JsonPath;
import com.metrotransit.exception.MetroTrasitBussinessException;
import com.metrotransit.utils.BusStopEnum;
import com.metrotransit.utils.ErrorMsgEnum;
import com.metrotransit.utils.MetrotransitConstants;

@Service
public class MetroTransitBusStopService implements BusStopService {

	@Value("${com.metrotransit.url.bus-stop}")
	private String BUS_STOP_URL;
	
	@Autowired
	MessageSource messageSource;
	
	@Override
	public String getBusStopsForRouteInDirection(String routeId, String directionId) {
		final String uri = MessageFormat.format(BUS_STOP_URL, routeId, directionId)+MetrotransitConstants.JSON_FORMAT;

		HttpHeaders headers = new HttpHeaders();
	    headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
		
	    RestTemplate restTemplate = new RestTemplate();
	    HttpEntity<String> entity = new HttpEntity<String>("parameters", headers);
	     
	    ResponseEntity<String> response = restTemplate.exchange(uri, HttpMethod.GET, entity, String.class);
	    
	    return response.getBody();
	}
	
	@Override
	public String findBusStopCodeForRoute(String routeId, String directionId, String busStopName) throws MetroTrasitBussinessException, NoSuchMessageException {
		String busStops = getBusStopsForRouteInDirection(routeId,directionId);
		
		List<Map<String, Object>> busRoute = JsonPath.parse(busStops)
				.read("$.[?(@."+BusStopEnum.TEXT.getKey()+"== '"+busStopName+"')]");
		
		if(null == busRoute || busRoute.size()==0)
			throw new MetroTrasitBussinessException(messageSource.getMessage(
					ErrorMsgEnum.INVALID_STOP.getValue(), new Object [] {busStopName}, Locale.ENGLISH));
		
		String busStopCode = busRoute.get(0).get(BusStopEnum.VALUE.getKey()).toString();
		
		return busStopCode;

	}
	
}
