package com.bexstech.exam.controller;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
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
import com.bexstech.exam.model.CheapestPath;
import com.bexstech.exam.model.Vertex;
import com.bexstech.exam.service.RouteScannerService;
import com.bexstech.exam.service.RouteService;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT, args = "--file=./resources/routes.csv")
public class RouteControllerTest {

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    @MockBean
    private RouteService routeService;

    @MockBean
    private RouteScannerService routeScannerService;

    @Test
    public void shouldRequestRoute() {
        URI targetUrl = UriComponentsBuilder.fromUriString("http://localhost:" + port + "/route")
                .queryParam("from", "GRU")
                .queryParam("to", "CDG")
                .build()
                .toUri();
        Vertex edges = new Vertex("GRU-CDG");
        CheapestPath cheapestPath = new CheapestPath(edges);
        cheapestPath.setPrice(20);

        doReturn(cheapestPath).when(routeService).find(any(), any());

        RouteResponseDTO routeResponseDTO = restTemplate.getForObject(targetUrl, RouteResponseDTO.class);

        assertAll("Route and Price",
                () -> assertEquals(routeResponseDTO.getRouteDescription(), "GRU-CDG"),
                () -> assertEquals(routeResponseDTO.getTotalPrice(), 20)
        );
        verify(routeService).find(any(), any());
    }

    @Test
    public void shouldInsertRoute() throws IOException {
        RouteDTO routeDTO = new RouteDTO("GRU", "FLN", 25);
        HttpEntity<RouteDTO> request = new HttpEntity<>(routeDTO);

        ResponseEntity<Void> response = restTemplate
                .exchange("http://localhost:" + port + "/route", HttpMethod.PUT,
                        request, Void.class);

        verify(routeService).insert(any());
    }
}
