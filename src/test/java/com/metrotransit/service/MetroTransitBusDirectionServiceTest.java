package com.metrotransit.service;

import static org.junit.Assert.assertTrue;

import java.util.List;
import java.util.Map;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.jayway.jsonpath.JsonPath;
import com.metrotransit.exception.MetroTrasitBussinessException;
import com.metrotransit.services.BusDirectionService;
import com.metrotransit.services.BusRouteService;

@RunWith(SpringRunner.class)
@SpringBootTest
public class MetroTransitBusDirectionServiceTest {

	@Autowired
	private BusDirectionService busDirectionService;

	@Autowired
	private BusRouteService busRouteService;

	private static String routeId="";
	
	private static final String SOUTHBOUND_CODE="4";

	@Before
	public void setup() throws MetroTrasitBussinessException {
		routeId = busRouteService.findRouteId("METRO Blue Line");
	}

	@Test
	public void testFindDirectionIdForRoute() throws MetroTrasitBussinessException {
		String directionId = busDirectionService.findDirectionIdForRoute(routeId, "north");
		assertTrue(SOUTHBOUND_CODE.equals(directionId));
	}

	@Test
	public void testGetDirectionsForRoute() throws MetroTrasitBussinessException {

		String response = busDirectionService.getDirectionsForRoute(routeId);

		List<Map<String, Object>> direction = JsonPath.parse(response)
				.read("$.[?(@.Text== 'NORTHBOUND')]");
		String routeId = direction.get(0).get("Value").toString();
		assertTrue(SOUTHBOUND_CODE.equals(routeId));
	}
	
	@Test(expected=MetroTrasitBussinessException.class)
	public void testFindInavlidDirectionIdForRoute() throws MetroTrasitBussinessException {
		String directionId = busDirectionService.findDirectionIdForRoute(routeId, "nor");
		assertTrue(SOUTHBOUND_CODE.equals(directionId));
	}
}
