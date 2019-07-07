package com.metrotransit.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.metrotransit.busfinder.service.BusFinder;
import com.metrotransit.dto.BusLocaterFilter;
import com.metrotransit.exception.MetroTransitTechnicalException;
import com.metrotransit.exception.MetroTrasitBussinessException;
import com.metrotransit.response.BusLocationResponse;
import com.metrotransit.utils.ResponseStatusCode;

@Controller
public class BusLocater {

	@Autowired
	@Qualifier("metroTransitBusFinder")
	private BusFinder<String, BusLocaterFilter>  busFinder;

	@RequestMapping 
	(value = "/find/bus/{routeName}/{direction}/{busStop}"
	,method =  RequestMethod.GET,
	produces = { "application/json" })
	@ResponseBody
	public ResponseEntity firstMyBus(@PathVariable("routeName") String routeName,
			@PathVariable("direction") String direction, @PathVariable("busStop") String busStop)
	{

		BusLocationResponse deptDetails = new BusLocationResponse();
		try {
			BusLocaterFilter filter = new BusLocaterFilter(routeName,direction,busStop);
			String response = busFinder.findNextBus(filter);


			deptDetails.setDepartureDetails(response);

			deptDetails.setStatusCode(ResponseStatusCode.SUCCESS.getCode());
			return new ResponseEntity(deptDetails, HttpStatus.OK);
		}catch(MetroTrasitBussinessException excep) {
			deptDetails.setStatusCode(ResponseStatusCode.FAILED.getCode());
			deptDetails.setText(excep.getMessage());
			return new ResponseEntity(deptDetails,HttpStatus.OK );
		}catch(MetroTransitTechnicalException excep) {
			deptDetails.setStatusCode(ResponseStatusCode.FAILED.getCode());
			deptDetails.setText(excep.getMessage());
			return new ResponseEntity(deptDetails,HttpStatus.OK );
		}catch(Exception excep) {
			deptDetails.setStatusCode(ResponseStatusCode.FAILED.getCode());
			deptDetails.setText("Failed to process the request.");
			return new ResponseEntity(deptDetails,HttpStatus.INTERNAL_SERVER_ERROR );
		}
		
	}

}
