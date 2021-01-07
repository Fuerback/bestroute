package com.bexstech.exam.service;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Arrays;
import java.util.List;
import java.util.NoSuchElementException;

import org.junit.Test;
import org.junit.jupiter.api.DisplayName;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.junit.MockitoJUnitRunner;

import com.bexstech.exam.dto.RouteResponseDTO;
import com.bexstech.exam.exception.InvalidInputException;
import com.bexstech.exam.dto.RouteDTO;

@RunWith(MockitoJUnitRunner.class)
public class RouteServiceTest {

	private static final List<RouteDTO> ROUTES = Arrays.asList(
			new RouteDTO( "GRU", "BRC", 10 ),
			new RouteDTO( "BRC", "SCL", 5 ),
			new RouteDTO( "GRU", "CDG", 75 ),
			new RouteDTO( "GRU", "SCL", 20 ),
			new RouteDTO( "GRU", "ORL", 56 ),
			new RouteDTO( "ORL", "CDG", 5 ),
			new RouteDTO( "SCL", "ORL", 20 )
	);

	@InjectMocks
	private RouteService routeService;

	@Test
	@DisplayName( "should find route" )
	public void shouldFindRoute() {
		String routeInput = "GRU-CDG";

		RouteResponseDTO routeResponseDTO = routeService.findRoute( routeInput, ROUTES );

		assertThat( routeResponseDTO.getTotalPrice() ).isEqualTo( 40 );
		assertThat( routeResponseDTO.getRouteDescription() ).isEqualTo( "GRU - BRC - SCL - ORL - CDG" );
	}

	@Test(expected = InvalidInputException.class)
	@DisplayName( "should ignore duplicate route" )
	public void shouldIgnoreDuplicateRoute() {
		String routeInput = "GRU-GRU";

		routeService.findRoute( routeInput, ROUTES );
	}

	@Test(expected = InvalidInputException.class)
	@DisplayName( "should ignore empty route" )
	public void shouldIgnoreEmptyRoute() {
		String routeInput = "";

		routeService.findRoute( routeInput, ROUTES );
	}

	@Test(expected = InvalidInputException.class)
	@DisplayName( "should ignore numeric route" )
	public void shouldIgnoreNumericRoute() {
		String routeInput = "G1R-CDG";

		routeService.findRoute( routeInput, ROUTES );
	}

	@Test(expected = NoSuchElementException.class)
	@DisplayName( "should ignore unknown route" )
	public void shouldIgnoreUnknownRoute() {
		String routeInput = "GRU-XXX";

		routeService.findRoute( routeInput, ROUTES );
	}
}
