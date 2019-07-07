package com.metrotransit.service;

import static org.junit.Assert.assertTrue;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.client.RestTemplate;

import com.jayway.jsonpath.Criteria;
import com.jayway.jsonpath.DocumentContext;
import com.jayway.jsonpath.Filter;
import com.jayway.jsonpath.JsonPath;
import com.metrotransit.exception.MetroTrasitBussinessException;
import com.metrotransit.services.BusRouteService;


@RunWith(SpringRunner.class)
@SpringBootTest
public class MetroTransitBusRouteServiceTest {

	
	@Autowired
	private BusRouteService busRouteService;
	
	@Test
	public void testGetRoutes() throws MetroTrasitBussinessException {
	    List<Map<String, Object>> busRoute = JsonPath.parse(busRouteService.getRoutes())
	    										.read("$.[?(@.Description== 'METRO Blue Line')]");
	    String busRouteStr = busRoute.get(0).get("Description").toString();
		assertTrue(busRoute != null && "METRO Blue Line".equals(busRouteStr));
	}
	
	@Test
	public void testGetRouteFor() throws MetroTrasitBussinessException {
		String route = busRouteService.getRouteFor("METRO Blue Line");
		String desc = JsonPath.read(route, "$.Description");
		assertTrue("METRO Blue Line".equals(desc));
	}
	
	@Test
	public void testFindRouteId() throws MetroTrasitBussinessException {
		String routeId = busRouteService.findRouteId("METRO Blue Line");
		assertTrue("901".equals(routeId));
	}
	
	@Test(expected=MetroTrasitBussinessException.class)
	public void testNegativeGetRouteFor() throws MetroTrasitBussinessException {
		String route = busRouteService.getRouteFor("INVALID ROUTE");
		String desc = JsonPath.read(route, "$.Description");
		assertTrue("METRO Blue Line".equals(desc));
	}
	
}
