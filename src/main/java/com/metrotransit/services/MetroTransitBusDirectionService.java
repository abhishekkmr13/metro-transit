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
import com.metrotransit.utils.ErrorMsgEnum;
import com.metrotransit.utils.MetrotransitConstants;

@Service
public class MetroTransitBusDirectionService implements BusDirectionService {

	@Value("${com.metrotransit.url.direction}")
	private String DIRECTION_URL;
	
	@Autowired
	MessageSource messageSource;
	
	@Override
	public String getDirectionsForRoute(String routeId) {
		final String uri = MessageFormat.format(DIRECTION_URL, routeId)+MetrotransitConstants.JSON_FORMAT;

		HttpHeaders headers = new HttpHeaders();
	    headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
		
	    RestTemplate restTemplate = new RestTemplate();
	    HttpEntity<String> entity = new HttpEntity<String>("parameters", headers);
	     
	    ResponseEntity<String> response = restTemplate.exchange(uri, HttpMethod.GET, entity, String.class);
	    
	    return response.getBody();
	}
	
	@Override
	public String findDirectionIdForRoute(String routeId, String direction) throws MetroTrasitBussinessException, NoSuchMessageException {
		String directions = getDirectionsForRoute(routeId);
		
		String directionBound = direction.toUpperCase()+MetrotransitConstants.DIRECTION_BOUND;
		List<Map<String, Object>> directionMap = JsonPath.parse(directions)
				.read("$.[?(@.Text== '"+directionBound+"')]");
		
		if(null == directionMap || directionMap.size()==0)
			throw new MetroTrasitBussinessException(messageSource.getMessage(
					ErrorMsgEnum.INVALID_DIRECTION.getValue(), new Object [] {direction}, Locale.ENGLISH));
		
		String directionId = directionMap.get(0).get("Value").toString();
		
		return directionId;

	}
}
