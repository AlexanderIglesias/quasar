package com.mercadolibre.challenge.quasar.response;

import com.mercadolibre.challenge.quasar.data.dto.PositionDTO;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Response {
	
	private PositionDTO position;
	private String message;
	private boolean success = true;
}
