package com.mercadolibre.challenge.quasar.data.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SatelliteDTO {

	private int id;
	private String name;
	private String[] message;
	private Float distance;
	private PositionDTO position;

	public SatelliteDTO(String name, String[] message, Float distance, PositionDTO position) {
		this.name = name;
		this.message = message;
		this.distance = distance;
		this.position = position;
	}
	
	public SatelliteDTO() {
		super();
	}



}
