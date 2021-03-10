package com.mercadolibre.challenge.quasar.data.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mercadolibre.challenge.quasar.data.dto.PositionDTO;
import com.mercadolibre.challenge.quasar.data.dto.SatelliteDTO;
import com.mercadolibre.challenge.quasar.data.dto.SatelliteList;
import com.mercadolibre.challenge.quasar.data.service.CommonServices;
import com.mercadolibre.challenge.quasar.data.utils.MessageUtils;
import com.mercadolibre.challenge.quasar.response.Response;
import com.mercadolibre.challenge.quasar.response.SatelliteException;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
public class ControllerTest {

	@MockBean
	private CommonServices commonServices;

	@Autowired
	private Controller controller;

	SatelliteList satelliteList;
	SatelliteDTO satellite;
	List<SatelliteDTO> listSat;

	@BeforeEach
	public void setup() throws Exception {

		MockitoAnnotations.initMocks(this);
		Mockito.when(commonServices.requestcreatesatellite(Mockito.any(), Mockito.any())).thenReturn(true);
	}

	@Test
	public void testGetLocation() throws Exception {

		configureSats();

		MockMvc mockMvc = MockMvcBuilders.standaloneSetup(controller).build();
		Response response = new Response();
		response.setMessage("este es un mensaje");
		response.setPosition(new PositionDTO(Float.parseFloat("80.3"), Float.parseFloat("-224.3")));
		Mockito.when(commonServices.calculateCoordinates(Mockito.any())).thenReturn(response);

		ObjectMapper mapper = new ObjectMapper();
		MockHttpServletRequestBuilder petition = post("/topsecret").accept(MediaType.APPLICATION_JSON)
				.contentType(MediaType.APPLICATION_JSON).content(mapper.writeValueAsString(satelliteList));
		mockMvc.perform(petition).andExpect(status().isOk());

	}

	@Test
	public void testGetLocationBadrequest() throws Exception {

		MockMvc mockMvc = MockMvcBuilders.standaloneSetup(controller).build();
		Response response = new Response();
		Mockito.when(commonServices.calculateCoordinates(Mockito.any())).thenReturn(response);
		satelliteList = new SatelliteList();

		ObjectMapper mapper = new ObjectMapper();
		response.setSuccess(Boolean.FALSE);
		response.setMessage(MessageUtils.WOOKIE_NOT_SAT);

		MockHttpServletRequestBuilder petition = post("/topsecret").accept(MediaType.APPLICATION_JSON)
				.contentType(MediaType.APPLICATION_JSON).content(mapper.writeValueAsString(satelliteList));
		mockMvc.perform(petition).andExpect(status().isBadRequest());

	}

	@Test
	public void testGetLocationResponseNotSuccess() throws Exception {

		configureSats();

		MockMvc mockMvc = MockMvcBuilders.standaloneSetup(controller).build();
		Response response = new Response();
		response.setMessage("este es un mensaje");
		response.setPosition(new PositionDTO(Float.parseFloat("80.3"), Float.parseFloat("-224.3")));
		response.setSuccess(false);
		Mockito.when(commonServices.calculateCoordinates(Mockito.any())).thenReturn(response);

		ObjectMapper mapper = new ObjectMapper();
		MockHttpServletRequestBuilder petition = post("/topsecret").accept(MediaType.APPLICATION_JSON)
				.contentType(MediaType.APPLICATION_JSON).content(mapper.writeValueAsString(satelliteList));
		mockMvc.perform(petition).andExpect(status().isNotFound());

	}

	@Test
	public void testGetLocationResponseNull() throws Exception {

		configureSats();

		MockMvc mockMvc = MockMvcBuilders.standaloneSetup(controller).build();
		Response response = new Response();
		response.setMessage("este es un mensaje");
		response.setPosition(new PositionDTO(Float.parseFloat("80.3"), Float.parseFloat("-224.3")));
		response.setSuccess(false);
		Mockito.when(commonServices.calculateCoordinates(Mockito.any())).thenReturn(null);

		ObjectMapper mapper = new ObjectMapper();
		MockHttpServletRequestBuilder petition = post("/topsecret").accept(MediaType.APPLICATION_JSON)
				.contentType(MediaType.APPLICATION_JSON).content(mapper.writeValueAsString(satelliteList));
		mockMvc.perform(petition).andExpect(status().isNotFound());

	}

	@Test
	public void testTopSecretSplitSatellite() throws Exception {

		MockMvc mockMvc = MockMvcBuilders.standaloneSetup(controller).build();
		String json = "{" + "\"distance\": 385.16," + "\"message\": [\"\", \"es\", \"\", \"\", \"secreto\"]" + "}";

		MockHttpServletRequestBuilder petition = post("/topsecret_split/kenobi/").accept(MediaType.APPLICATION_JSON)
				.contentType(MediaType.APPLICATION_JSON).content(json);
		mockMvc.perform(petition).andExpect(status().isBadRequest());

	}

	@Test
	public void testTopSecretSplitSatelliteInternalServer() throws Exception {

		MockMvc mockMvc = MockMvcBuilders.standaloneSetup(controller).build();
		Mockito.when(commonServices.requestcreatesatellite(Mockito.any(), Mockito.any())).thenThrow(new Exception());
		String json = "{" + "\"distance\": 385.16," + "\"message\": [\"\", \"es\", \"\", \"\", \"secreto\"]" + "}";

		MockHttpServletRequestBuilder petition = post("/topsecret_split/kenobi/").accept(MediaType.APPLICATION_JSON)
				.contentType(MediaType.APPLICATION_JSON).content(json);
		mockMvc.perform(petition).andExpect(status().isInternalServerError());

	}

	@Test
	public void testTopSecretSplitSatelliteOk() throws Exception {

		MockMvc mockMvc = MockMvcBuilders.standaloneSetup(controller).build();
		Mockito.when(commonServices.requestcreatesatellite(Mockito.any(), Mockito.any())).thenReturn(true);
		String json = "{" + "\"distance\": 385.16," + "\"message\": [\"\", \"es\", \"\", \"\", \"secreto\"]" + "}";

		MockHttpServletRequestBuilder petition = post("/topsecret_split/kenobi/").accept(MediaType.APPLICATION_JSON)
				.contentType(MediaType.APPLICATION_JSON).content(json);
		mockMvc.perform(petition).andExpect(status().isCreated());

	}

	@Test
	public void testTopSecretSplitSolution() throws Exception {

		MockMvc mockMvc = MockMvcBuilders.standaloneSetup(controller).build();
		Response response = new Response();

		response.setMessage("este es un mensaje");

		response.setPosition(new PositionDTO(Float.parseFloat("80.3"), Float.parseFloat("-224.3")));

		Mockito.when(commonServices.getLocationSplit()).thenReturn(response);

		MockHttpServletRequestBuilder petition = get("/topsecret_split").accept(MediaType.APPLICATION_JSON)
				.contentType(MediaType.APPLICATION_JSON);

		mockMvc.perform(petition).andExpect(status().isOk());

	}

	@Test
	public void getTopSecretSplitFailedTest() throws Exception {
		MockMvc mockMvc = MockMvcBuilders.standaloneSetup(controller).build();
		Response response = null;
		Mockito.when(commonServices.getLocationSplit()).thenReturn(response);

		MockHttpServletRequestBuilder petition = get("/topsecret_split").accept(MediaType.APPLICATION_JSON)
				.contentType(MediaType.APPLICATION_JSON);
		mockMvc.perform(petition).andExpect(status().isNotFound());

	}

	@Test
	public void getTopSecretSplitFailedQuasarExceptionTest() throws Exception {
		MockMvc mockMvc = MockMvcBuilders.standaloneSetup(controller).build();
		Mockito.when(commonServices.getLocationSplit())
				.thenThrow(new SatelliteException("No se encontro respuesta con los datos", null));

		MockHttpServletRequestBuilder petition = get("/topsecret_split").accept(MediaType.APPLICATION_JSON)
				.contentType(MediaType.APPLICATION_JSON);

		mockMvc.perform(petition).andExpect(status().isNotFound());
	}


	private void configureSats() {
		satelliteList = new SatelliteList();

		listSat = new ArrayList<>();
		String[] m1 = { "este", "", "", "mensaje", "" };
		String[] m2 = { "este", "", "", "mensaje", "" };
		String[] m3 = { "este", "", "", "mensaje", "" };

		listSat.add(new SatelliteDTO("kenobi", m1, (float) 603.0, null));
		listSat.add(new SatelliteDTO("skywalker", m2, (float) 385.16, null));
		listSat.add(new SatelliteDTO("sato", m3, (float) 601.06, null));

		satelliteList.setSatellites(listSat);
	}

}
