package com.metrotransit.service;

import static org.junit.Assert.assertTrue;

import java.util.List;
import java.util.Map;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.transaction.BeforeTransaction;

import com.jayway.jsonpath.JsonPath;
import com.metrotransit.exception.MetroTrasitBussinessException;
import com.metrotransit.services.BusDirectionService;
import com.metrotransit.services.BusRouteService;
import com.metrotransit.services.BusStopService;

@RunWith(SpringRunner.class)
@SpringBootTest
public class MetroTransitBusStopServiceTest {

	@Autowired
	private BusStopService busStopService;

	@Autowired
	private BusDirectionService busDirectionService;

	@Autowired
	private BusRouteService busRouteService;

	private static String routeId;
	private static String directionId;

	private static final String BUS_STOP_NAME="Mall of America Station";
	private static final String BUS_STOP_CODE="MAAM";

	@Before
	public void  setup() throws MetroTrasitBussinessException {
		routeId = busRouteService.findRouteId("METRO Blue Line");
		directionId = busDirectionService.findDirectionIdForRoute(routeId, "north");
	}

	@Test
	public void testGetBusStopsForRouteInDirection() throws MetroTrasitBussinessException {
		String response = busStopService.getBusStopsForRouteInDirection(routeId, directionId);

		List<Map<String, Object>> busRoute = JsonPath.parse(response)
				.read("$.[?(@.Text== '"+BUS_STOP_NAME+"')]");
		String busStopCode = busRoute.get(0).get("Value").toString();
		
		assertTrue(BUS_STOP_CODE.equals(busStopCode));
	}

	@Test
	public void testFindBusStopCodeForRoute() throws MetroTrasitBussinessException {
		String busStopCode = busStopService.findBusStopCodeForRoute(routeId, directionId, BUS_STOP_NAME);
		assertTrue(BUS_STOP_CODE.equals(busStopCode));
	}
	
	@Test(expected=MetroTrasitBussinessException.class)
	public void testFindInvalidBusStopCodeForRoute() throws MetroTrasitBussinessException {
		String busStopCode = busStopService.findBusStopCodeForRoute(routeId, directionId, BUS_STOP_NAME+"Invalid");
		assertTrue(BUS_STOP_CODE.equals(busStopCode));
	}

}
