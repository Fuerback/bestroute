package com.bexstech.exam.controller;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.verify;

import java.io.IOException;
import java.net.URI;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.util.UriComponentsBuilder;

import com.bexstech.exam.dto.RouteDTO;
import com.bexstech.exam.dto.RouteResponseDTO;
import com.bexstech.exam.service.RouteService;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT, args = "--file=/docs/routes.csv")
public class RouteControllerTest {

	@LocalServerPort
	private int port;

	@Autowired
	private TestRestTemplate restTemplate;

	@MockBean
	private RouteService routeService;

	@Test
	public void shouldRequestRoute() {
		URI targetUrl= UriComponentsBuilder.fromUriString("http://localhost:" + port + "/route")
				.queryParam("from", "GRU")
				.queryParam("to", "CDG")
				.build()
				.toUri();

		doReturn( new RouteResponseDTO("GRU-CDG", 20) ).when( routeService ).findRoute( anyString(), any() );

		String forObject = restTemplate.getForObject( targetUrl, String.class );

		assertThat(forObject).contains("GRU-CDG > $20");
		verify( routeService ).findRoute( anyString(), any() );
	}

	@Test
	public void shouldInsertRoute() throws IOException {
		RouteDTO routeDTO = new RouteDTO( "GRU", "FLN", 25 );
		HttpEntity<RouteDTO> request = new HttpEntity<>( routeDTO );

		ResponseEntity<Void> response = restTemplate
				.exchange( "http://localhost:" + port + "/route", HttpMethod.PUT,
						request, Void.class );

		verify( routeService ).insertRoute( any() );
	}
}
