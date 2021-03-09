package com.mercadolibre.challenge.quasar.response;

public class SatelliteException extends Exception{

	private static final long serialVersionUID = -8528655930668593749L;
	
	public SatelliteException(String message, Throwable error) {
		super(message);
	}

}
