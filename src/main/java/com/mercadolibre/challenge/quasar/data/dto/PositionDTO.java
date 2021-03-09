package com.mercadolibre.challenge.quasar.data.dto;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@RequiredArgsConstructor
public class PositionDTO {

	private int id;
	private Float x;
	private Float y;

	public PositionDTO(Float x, Float y) {
		super();
		this.x = x;
		this.y = y;
	}

}
