package com.mercadolibre.challenge.quasar.data.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import org.dozer.Mapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.mercadolibre.challenge.quasar.data.dto.PositionDTO;
import com.mercadolibre.challenge.quasar.data.dto.SatelliteDTO;
import com.mercadolibre.challenge.quasar.data.dto.SatelliteList;
import com.mercadolibre.challenge.quasar.data.service.impl.db.entity.SatelliteEntity;
import com.mercadolibre.challenge.quasar.data.utils.MathUtils;
import com.mercadolibre.challenge.quasar.response.Response;
import com.mercadolibre.challenge.quasar.response.SatelliteException;

@Service
public class CommonServices {

	@Autowired
	SatelliteService satelliteService;

	@Autowired
	MathUtils utils;

	@Autowired
	@Qualifier("quasarMapper")
	private Mapper mapper;

	public Response calculateCoordinates(SatelliteList sats) throws SatelliteException {
		Response response = new Response();

		List<SatelliteDTO> listSat = findAndConfigureSats(sats);
		if (!validateSats(listSat)) {
			response.setMessage("error in satcom");
			return response;
		}

		return satelliteService.getLocation(listSat);

	}

	private List<SatelliteDTO> findAndConfigureSats(SatelliteList sats) {

		List<SatelliteDTO> listSat = new ArrayList<>();

		configureKenobi(sats, listSat);

		configureSkywalker(sats, listSat);

		cofigureSato(sats, listSat);

		return listSat;

	}

	private void configureKenobi(SatelliteList sats, List<SatelliteDTO> listSat) {
		SatelliteDTO kenobi = satelliteService.findSat("Kenobi", sats.getSatellites());

		if (kenobi != null) {
			listSat.add(buildSat(kenobi.getName(), kenobi.getMessage(), kenobi.getDistance(), Float.valueOf(-500),
					Float.valueOf(-200)));
		}
	}

	private void configureSkywalker(SatelliteList sats, List<SatelliteDTO> listSat) {
		SatelliteDTO skyWalker = satelliteService.findSat("Skywalker", sats.getSatellites());
		if (skyWalker != null) {
			listSat.add(buildSat(skyWalker.getName(), skyWalker.getMessage(), skyWalker.getDistance(),
					Float.valueOf(100), Float.valueOf(-100)));
		}
	}

	private void cofigureSato(SatelliteList sats, List<SatelliteDTO> listSat) {
		SatelliteDTO sato = satelliteService.findSat("Sato", sats.getSatellites());
		if (sato != null) {
			listSat.add(buildSat(sato.getName(), sato.getMessage(), sato.getDistance(), Float.valueOf(500),
					Float.valueOf(100)));
		}
	}

	private SatelliteDTO buildSat(String name, String[] message, Float distance, Float x, Float y) {

		PositionDTO p = new PositionDTO(x, y);

		return new SatelliteDTO(name, message, distance, p);

	}

	private boolean validateSats(List<SatelliteDTO> sats) {

		if (sats.isEmpty() || sats.size() < 3) {
			return false;
		}

		SatelliteDTO kenobi = satelliteService.findSat("Kenobi", sats);
		SatelliteDTO skyWalker = satelliteService.findSat("Skywalker", sats);
		SatelliteDTO sato = satelliteService.findSat("Sato", sats);

		if (Objects.isNull(kenobi) || Objects.isNull(skyWalker) || Objects.isNull(sato)) {
			return false;
		}

		double d1 = calculateDistance(kenobi.getPosition(), skyWalker.getPosition());

		if (d1 > kenobi.getDistance() + skyWalker.getDistance()) {
			return false;
		}

		double d2 = calculateDistance(skyWalker.getPosition(), sato.getPosition());

		if (d2 > skyWalker.getDistance() + sato.getDistance()) {
			return false;
		}

		double d3 = calculateDistance(kenobi.getPosition(), sato.getPosition());

		return (d3 <= (kenobi.getDistance() + sato.getDistance()));

	}

	private double calculateDistance(PositionDTO p1, PositionDTO p2) {
		return utils.distance(p1, p2);

	}

	public Response getLocationSplit() throws SatelliteException {
		List<SatelliteEntity> satellitesEntity = satelliteService.getAllSats();
		List<SatelliteDTO> satelliteList = new ArrayList<>();
		if (!satellitesEntity.isEmpty()) {
			for (SatelliteEntity entity : satellitesEntity) {
				SatelliteDTO satellite = mapper.map(entity, SatelliteDTO.class, "entityToDTO");
				satelliteList.add(satellite);
			}
		}
		if (!satelliteList.isEmpty()) {
			return satelliteService.getLocation(satelliteList);
		}
		return null;
	}

	@Transactional(propagation = Propagation.REQUIRED, readOnly = false)
	public boolean requestcreatesatellite(String satelliteName, SatelliteDTO satellite) throws Exception {

		satellite.setName(satelliteName);

		SatelliteEntity entity = mapper.map(satellite, SatelliteEntity.class, "dtoToEntity");
		SatelliteEntity entityCreated = satelliteService.getByName(entity.getName());
		if (entityCreated != null) {
			entity.setId(entityCreated.getId());
		}
		entity = satelliteService.save(entity);
		return entity != null;

	}

}
