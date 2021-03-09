package com.mercadolibre.challenge.quasar.data.service;

import java.util.List;

import com.mercadolibre.challenge.quasar.data.dto.SatelliteDTO;
import com.mercadolibre.challenge.quasar.data.service.impl.db.entity.SatelliteEntity;
import com.mercadolibre.challenge.quasar.response.Response;
import com.mercadolibre.challenge.quasar.response.SatelliteException;

public interface SatelliteService {
	
	public String getMessage(List<SatelliteDTO> listSat) throws SatelliteException;
	public Response getLocation(List<SatelliteDTO> listSat) throws SatelliteException;
	public SatelliteDTO findSat(String name, List<SatelliteDTO> sats);
	public List<SatelliteEntity> getAllSats();
	public SatelliteEntity getByName(String name);
	public SatelliteEntity save(SatelliteEntity entity);
	

}
