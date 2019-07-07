package com.metrotransit.busfinder.service;

import com.metrotransit.exception.BaseException;

public interface BusFinder <T,U>{

	
	public T findNextBus(U inputParam) throws BaseException;
	
}
