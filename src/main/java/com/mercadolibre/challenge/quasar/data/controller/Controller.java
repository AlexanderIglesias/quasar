package com.mercadolibre.challenge.quasar.data.controller;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import org.apache.commons.lang3.exception.ExceptionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.mercadolibre.challenge.quasar.data.dto.SatelliteDTO;
import com.mercadolibre.challenge.quasar.data.dto.SatelliteList;
import com.mercadolibre.challenge.quasar.data.service.CommonServices;
import com.mercadolibre.challenge.quasar.data.utils.MessageUtils;
import com.mercadolibre.challenge.quasar.response.Response;
import com.mercadolibre.challenge.quasar.response.SatelliteException;

@RestController
@Validated
public class Controller {
	private static final Logger LOGGER = LoggerFactory.getLogger(Controller.class);

	@Autowired
	CommonServices commonServices;

	@PostMapping
	@CrossOrigin
	@RequestMapping(path = "/topsecret")
	public @ResponseBody ResponseEntity<Response> getLocation(
			@Valid @NotNull(message = "SATELLITE REQUIRED") @RequestBody SatelliteList sats) throws SatelliteException {
		Response response = null;
		HttpStatus status = HttpStatus.OK;
		if (sats.getSatellites() == null) {
			status = HttpStatus.BAD_REQUEST;
			response = new Response();
			response.setSuccess(Boolean.FALSE);
			response.setMessage(MessageUtils.WOOKIE_NOT_SAT);
			LOGGER.info(response.getMessage());
			return ResponseEntity.status(status).body(response);
		}

		response = commonServices.calculateCoordinates(sats);
		if (response != null && !response.isSuccess()) {
			status = HttpStatus.NOT_FOUND;
			LOGGER.info(response.getMessage());
			return ResponseEntity.status(status).body(response);
		}
		if(response==null) {
			status = HttpStatus.NOT_FOUND;
			LOGGER.info("response is null");
		}
		return ResponseEntity.status(status).body(response);
	}

	@CrossOrigin
	@PostMapping(value = "/topsecret_split/{satellite_name}")
	public @ResponseBody ResponseEntity<Response> topSecretSplitSatellite(
			@Valid @NotNull(message = "NAME REQUIRED") @PathVariable("satellite_name") String satelliteName,
			@RequestBody SatelliteDTO satellite) {
		Response response = new Response();
		try {
			boolean created = commonServices.requestcreatesatellite(satelliteName, satellite);
			if (!created) {
				response.setMessage(MessageUtils.WOOKIE_PROBLEM);
				response.setSuccess(created);
				LOGGER.info(MessageUtils.WOOKIE_PROBLEM);
				return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
			}
		} catch (Exception e) {
			LOGGER.error(e.getMessage());
			LOGGER.error(ExceptionUtils.getStackTrace(e));
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
		}
		response.setMessage(MessageUtils.WOOKIE_OK);
		response.setSuccess(Boolean.TRUE);
		return ResponseEntity.status(HttpStatus.CREATED).body(response);
	}

	@CrossOrigin
	@GetMapping(value = "/topsecret_split")
	public @ResponseBody ResponseEntity<Response> topSecretSplitSolution() {
		Response response = null;
		try {
			response = commonServices.getLocationSplit();
			if (response == null || response.getMessage() == null || response.getPosition() == null) {
				response = new Response();
				response.setMessage(MessageUtils.WOOKIE_PROBLEM);
				response.setSuccess(Boolean.FALSE);
				return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
			}
		} catch (SatelliteException e) {
			LOGGER.error(e.getMessage());
			response = new Response();
			response.setMessage(e.getMessage());
			response.setSuccess(Boolean.FALSE);
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
		} catch (Exception e) {
			LOGGER.error(e.getMessage());
			LOGGER.error(ExceptionUtils.getStackTrace(e));
			response = new Response();
			response.setMessage(e.getMessage());
			response.setSuccess(Boolean.FALSE);
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
		}
		return ResponseEntity.ok(response);
	}

}
