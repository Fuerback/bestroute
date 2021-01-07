package com.bexstech.exam.service;

import static org.assertj.core.api.Assertions.assertThat;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import org.junit.Test;
import org.junit.jupiter.api.DisplayName;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.MockedStatic;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

import com.bexstech.exam.dto.RouteDTO;
import com.bexstech.exam.dto.RouteResponseDTO;
import com.bexstech.exam.exception.BadRequestException;
import com.bexstech.exam.util.WriteFile;

@RunWith(MockitoJUnitRunner.class)
public class RouteServiceTest {

	private static final List<RouteDTO> ROUTES = Arrays.asList(
			new RouteDTO( "GRU", "BRC", 10 ),
			new RouteDTO( "BRC", "SCL", 5 ),
			new RouteDTO( "GRU", "CDG", 75 ),
			new RouteDTO( "GRU", "SCL", 20 ),
			new RouteDTO( "GRU", "ORL", 56 ),
			new RouteDTO( "ORL", "CDG", 5 ),
			new RouteDTO( "SCL", "ORL", 20 ),
			new RouteDTO( "GRU", "FLN", 25 ),
			new RouteDTO( "FLN", "CRT", 25 ),
			new RouteDTO( "FLN", "GRU", 25 )
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

	@Test
	@DisplayName( "should find route and ignore cyclic routes" )
	public void shouldFindRouteAndIgnoreCyclicRoute() {
		String routeInput = "GRU-CRT";

		RouteResponseDTO routeResponseDTO = routeService.findRoute( routeInput, ROUTES );

		assertThat( routeResponseDTO.getTotalPrice() ).isEqualTo( 50 );
		assertThat( routeResponseDTO.getRouteDescription() ).isEqualTo( "GRU - FLN - CRT" );
	}

	@Test(expected = BadRequestException.class)
	@DisplayName( "should ignore cyclic route" )
	public void shouldIgnoreCyclicRoute() {
		String routeInput = "GRU-GRU";

		routeService.findRoute( routeInput, ROUTES );
	}

	@Test(expected = BadRequestException.class)
	@DisplayName( "should ignore empty route" )
	public void shouldIgnoreEmptyRoute() {
		String routeInput = "";

		routeService.findRoute( routeInput, ROUTES );
	}

	@Test(expected = BadRequestException.class)
	@DisplayName( "should ignore numeric route" )
	public void shouldIgnoreNumericRoute() {
		String routeInput = "G1R-CDG";

		routeService.findRoute( routeInput, ROUTES );
	}

	@Test(expected = BadRequestException.class)
	@DisplayName( "should ignore unknown route" )
	public void shouldIgnoreUnknownRoute() {
		String routeInput = "GRU-XXX";

		routeService.findRoute( routeInput, ROUTES );
	}

	@Test
	@DisplayName( "should insert new route" )
	public void shouldInsertNewRoute() throws IOException {
		try (MockedStatic<WriteFile> mockedUuid = Mockito.mockStatic(WriteFile.class)) {
			RouteDTO newRoute = new RouteDTO( "FLN", "BEL", 35 );

			routeService.insertRoute( newRoute );
		}
	}

	@Test(expected = BadRequestException.class)
	@DisplayName( "should not insert route with no price" )
	public void shouldNotInsertRouteWithNoPrice() throws IOException {
		RouteDTO newRoute = new RouteDTO( "FLN", "BEL", null );

		routeService.insertRoute( newRoute );
	}

	@Test(expected = BadRequestException.class)
	@DisplayName( "should not insert cyclic route" )
	public void shouldNotInsertCyclicRoute() throws IOException {
		RouteDTO newRoute = new RouteDTO( "FLN", "FLN", 0 );

		routeService.insertRoute( newRoute );
	}

	@Test(expected = BadRequestException.class)
	@DisplayName( "should not insert empty route" )
	public void shouldNotInsertEmptyRoute() throws IOException {
		RouteDTO newRoute = new RouteDTO( "FLN", "", 0 );

		routeService.insertRoute( newRoute );
	}
}
