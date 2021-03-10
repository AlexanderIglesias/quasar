package com.mercadolibre.challenge.quasar.data.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mercadolibre.challenge.quasar.data.dto.PositionDTO;
import com.mercadolibre.challenge.quasar.data.dto.SatelliteDTO;
import com.mercadolibre.challenge.quasar.data.service.SatelliteService;
import com.mercadolibre.challenge.quasar.data.service.impl.db.entity.SatelliteEntity;
import com.mercadolibre.challenge.quasar.data.service.impl.db.repositories.SatelliteRepository;
import com.mercadolibre.challenge.quasar.data.utils.MathUtils;
import com.mercadolibre.challenge.quasar.data.utils.MessageUtils;
import com.mercadolibre.challenge.quasar.response.Response;
import com.mercadolibre.challenge.quasar.response.SatelliteException;

@Service
public class SatelliteServiceImpl implements SatelliteService {

	@Autowired
	MathUtils utils;

	@Autowired
	MessageUtils messageUtils;

	@Autowired
	private SatelliteRepository satelliteRepository;

	@Override
	public Response getLocation(List<SatelliteDTO> listSat) throws SatelliteException {

		Response response = new Response();
		PositionDTO p = null;
		SatelliteDTO kenobi = this.findSat("Kenobi", listSat);
		SatelliteDTO sato = this.findSat("Sato", listSat);
		SatelliteDTO skyWalker = this.findSat("Skywalker", listSat);

		if (Objects.nonNull(kenobi) && Objects.nonNull(sato) && Objects.nonNull(skyWalker)) {
			p = utils.calculatePosition(kenobi, sato, skyWalker);
		}

		response.setMessage(this.getMessage(listSat));
		response.setPosition(p);

		return response;
	}

	@Override
	public String getMessage(List<SatelliteDTO> listSat) throws SatelliteException {

		List<String[]> listMessges = new ArrayList<>();
		List<String> completeMessage = new ArrayList<>();
		listMessges.addAll(getMessageForSats(listSat, "Kenobi"));
		listMessges.addAll(getMessageForSats(listSat, "Skywalker"));
		listMessges.addAll(getMessageForSats(listSat, "Sato"));

		if (!listMessges.isEmpty()) {
			listMessges = this.prepareMessage(listMessges);
			for (String[] msg : listMessges) {
				completeMessage = messageUtils.getWords(completeMessage, msg);
			}
			return messageUtils.formatMessage(completeMessage);
		}
		return null;

		
	}

	private List<String[]> prepareMessage(List<String[]> messages) {
		return messageUtils.iterateToRemove(messages);
	}

	@Override
	public SatelliteDTO findSat(String name, List<SatelliteDTO> sats) {

		return sats.stream().filter(sat -> name.equalsIgnoreCase(sat.getName())).findAny().orElse(null);

	}

	private List<String[]> getMessageForSats(List<SatelliteDTO> listSat, String name) {

		return listSat.stream().filter(sat -> name.equalsIgnoreCase(sat.getName())).map(SatelliteDTO::getMessage)
				.collect(Collectors.toList());

	}

	@Override
	public List<SatelliteEntity> getAllSats() {
		return satelliteRepository.findAll();
	}

	@Override
	public SatelliteEntity getByName(String name) {
		return satelliteRepository.findByName(name);
	}

	@Override
	public SatelliteEntity save(SatelliteEntity entity) {
		return satelliteRepository.save(entity);
	}

}
