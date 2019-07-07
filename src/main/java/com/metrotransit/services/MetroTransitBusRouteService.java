package com.metrotransit.services;

import java.util.Arrays;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.web.servlet.WebMvcProperties.LocaleResolver;
import org.springframework.context.MessageSource;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
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
import com.metrotransit.utils.BusRouteEnum;
import com.metrotransit.utils.ErrorMsgEnum;
import com.metrotransit.utils.MetrotransitConstants;

@Service
public class MetroTransitBusRouteService implements BusRouteService {

	@Value("${com.metrotransit.url.route}")
	private String ROUTE_URL;

	@Autowired
	MessageSource messageSource;
	
	@Override
	public String getRoutes() {

		final String uri = ROUTE_URL+MetrotransitConstants.JSON_FORMAT;

		HttpHeaders headers = new HttpHeaders();
		headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));

		RestTemplate restTemplate = new RestTemplate();
		HttpEntity<String> entity = new HttpEntity<String>("parameters", headers);

		ResponseEntity<String> response = restTemplate.exchange(uri, HttpMethod.GET, entity, String.class);

		return response.getBody();

	}

	@Override
	public String getRouteFor(String routeName) throws MetroTrasitBussinessException {

		List<Map<String, Object>> response = JsonPath.parse(getRoutes())
				.read("$.[?(@."+BusRouteEnum.DESC.getKey()+" == '"+routeName+"')]");

		if(null == response || response.size()==0) {
			 ReloadableResourceBundleMessageSource messageSource
		      = new ReloadableResourceBundleMessageSource();
		     
		    messageSource.setBasename("classpath:messages/error-messages");
		    messageSource.setDefaultEncoding("UTF-8");
			
			throw new MetroTrasitBussinessException(messageSource.getMessage(
					ErrorMsgEnum.INVALID_ROUTE.getValue(), new Object [] {routeName}, Locale.ENGLISH));
		}
		Gson gsonObj = new Gson();
		String jsonStr = gsonObj.toJson(response.get(0));
		return jsonStr;
	}

	@Override
	public String findRouteId(String routeName) throws MetroTrasitBussinessException {
		String route = getRouteFor(routeName);

		String routeId = JsonPath.read(route, "$."+BusRouteEnum.ROUTE_ID.getKey());

		return routeId.toString();

	}

}
