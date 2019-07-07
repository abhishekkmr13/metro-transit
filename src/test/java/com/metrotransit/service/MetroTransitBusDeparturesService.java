package com.metrotransit.service;

import static org.junit.Assert.assertNotNull;
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
import com.metrotransit.services.BusDeparturesService;
import com.metrotransit.services.BusDirectionService;
import com.metrotransit.services.BusRouteService;
import com.metrotransit.services.BusStopService;


@RunWith(SpringRunner.class)
@SpringBootTest
public class MetroTransitBusDeparturesService {

	@Autowired
	private BusStopService busStopService;

	@Autowired
	private BusDirectionService busDirectionService;

	@Autowired
	private BusRouteService busRouteService;
	
	@Autowired
	private BusDeparturesService busDeparturesService;
	
	private static String routeId;
	private static String directionId;
	private static String busStopCode;

	private static final String BUS_STOP_NAME="Mall of America Station";
	private static final String BUS_STOP_CODE="MAAM";
	
	
	@Before
	public void setUp() throws Exception {
		
		routeId = busRouteService.findRouteId("METRO Blue Line");
		directionId = busDirectionService.findDirectionIdForRoute(routeId, "north");
		busStopCode = busStopService.findBusStopCodeForRoute(routeId, directionId, BUS_STOP_NAME);
	}

	@Test
	public void testFindDepartureDetailsFor() throws MetroTrasitBussinessException {
		
		String response = busDeparturesService.findDepartureDetailsOfNearestBuses("METRO Blue Line", "north", BUS_STOP_NAME);
		
		List<Map<String, Object>> responseMap = JsonPath.parse(response)
				.read("$");
		
		assertTrue(responseMap.get(0).get("DepartureText") != null);
	}
	
	@Test
	public void testFindDepartureDetailsForNearestBus() throws MetroTrasitBussinessException {
		String response = busDeparturesService.findDepartureDetailsForNearestBus("METRO Blue Line", "north", BUS_STOP_NAME);
		String departureTime = JsonPath.read(response, "$.DepartureText");
		
		assertNotNull(departureTime);
	}
	
	@Test
	public void testFindDepartureTimeFor() throws MetroTrasitBussinessException {
		String departureTime = busDeparturesService.findDepartureTimeOfNearestBus("METRO Blue Line", "north", BUS_STOP_NAME);
		assertNotNull(departureTime);
	}
	

}
