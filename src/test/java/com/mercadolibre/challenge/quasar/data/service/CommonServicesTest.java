package com.mercadolibre.challenge.quasar.data.service;

import java.util.ArrayList;
import java.util.List;

import org.dozer.Mapper;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.mercadolibre.challenge.quasar.data.dto.PositionDTO;
import com.mercadolibre.challenge.quasar.data.dto.SatelliteDTO;
import com.mercadolibre.challenge.quasar.data.dto.SatelliteList;
import com.mercadolibre.challenge.quasar.data.utils.MathUtils;
import com.mercadolibre.challenge.quasar.response.Response;
import com.mercadolibre.challenge.quasar.response.SatelliteException;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
public class CommonServicesTest {

	@Autowired
	SatelliteService satelliteService;

	@Autowired
	CommonServices commonServices;

	@Autowired
	MathUtils utils;

	@Autowired
	@Qualifier("quasarMapper")
	private Mapper mapper;

	SatelliteList satelliteList;
	SatelliteDTO satellite;
	List<SatelliteDTO> listSat;

	@BeforeEach
	public void setup() throws SatelliteException {
		MockitoAnnotations.initMocks(this);
	}

	@Test
	public void testCalculateCoordinates() throws Exception {
		configureSats();

		Response response = commonServices.calculateCoordinates(satelliteList);
		Assertions.assertNotEquals(null, response);
		Assertions.assertEquals("-85.34598", response.getPosition().getX() + "");
		Assertions.assertEquals("238.3797", response.getPosition().getY() + "");
	}

	@Test
	public void testCalculateCoordinatesNotValid() throws Exception {
		satelliteList = new SatelliteList();

		listSat = new ArrayList<>();
		String[] m1 = { "este", "", "", "mensaje", "" };
		String[] m2 = { "este", "", "", "mensaje", "" };
		String[] m3 = { "este", "", "", "mensaje", "" };

		listSat.add(new SatelliteDTO("kenobi", m1, (float) 100.0,
				new PositionDTO(Float.valueOf(-500), Float.valueOf(-200))));
		listSat.add(new SatelliteDTO("skywalker", m2, (float) 115.5,
				new PositionDTO(Float.valueOf(100), Float.valueOf(-100))));
		listSat.add(
				new SatelliteDTO("sato", m3, (float) 142.76, new PositionDTO(Float.valueOf(500), Float.valueOf(100))));

		satelliteList.setSatellites(listSat);
		String msg = "error in satcom";
		Response response = commonServices.calculateCoordinates(satelliteList);
		Assertions.assertNotEquals(null, response);
		Assertions.assertEquals(msg, response.getMessage());

	}

	@Test
	public void testGetLocationSplit() throws Exception {

		Response response = commonServices.getLocationSplit();
		Assertions.assertEquals(null, response);
	}

	@Test
	public void testRequestCreateSatellite() throws Exception {
		SatelliteDTO satellite = new SatelliteDTO();
		String[] m1 = { "este", "", "", "mensaje", "" };
		String name = "Kenobi";
		satellite.setDistance((float) 603.0);
		satellite.setMessage(m1);
		boolean response = commonServices.requestcreatesatellite(name, satellite);
		Assertions.assertEquals(Boolean.TRUE, response);
	}

	private void configureSats() {
		satelliteList = new SatelliteList();

		listSat = new ArrayList<>();
		String[] m1 = { "este", "", "", "mensaje", "" };
		String[] m2 = { "este", "", "", "mensaje", "" };
		String[] m3 = { "este", "", "", "mensaje", "" };

		listSat.add(new SatelliteDTO("kenobi", m1, (float) 603.0,
				new PositionDTO(Float.valueOf(-500), Float.valueOf(-200))));
		listSat.add(new SatelliteDTO("skywalker", m2, (float) 385.16,
				new PositionDTO(Float.valueOf(100), Float.valueOf(-100))));
		listSat.add(
				new SatelliteDTO("sato", m3, (float) 601.06, new PositionDTO(Float.valueOf(500), Float.valueOf(100))));

		satelliteList.setSatellites(listSat);
	}

}
