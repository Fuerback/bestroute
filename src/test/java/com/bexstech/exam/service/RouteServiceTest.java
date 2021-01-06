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

import com.bexstech.exam.exception.InvalidInputException;
import com.bexstech.exam.models.RouteModel;
import com.bexstech.exam.services.RouteService;

@RunWith(MockitoJUnitRunner.class)
public class RouteServiceTest {

	private static final List<RouteModel> ROUTES = Arrays.asList(
			new RouteModel( "GRU", "BRC", 10 ),
			new RouteModel( "BRC", "SCL", 5 ),
			new RouteModel( "GRU", "CDG", 75 ),
			new RouteModel( "GRU", "SCL", 20 ),
			new RouteModel( "GRU", "ORL", 56 ),
			new RouteModel( "ORL", "CDG", 5 ),
			new RouteModel( "SCL", "ORL", 20 )
	);

	@InjectMocks
	private RouteService routeService;

	@Test
	@DisplayName( "should find route" )
	public void shouldFindRoute() {
		String routeInput = "GRU-CDG";

		String bestRoute = routeService.findBestRoute( routeInput, ROUTES );

		assertThat( bestRoute ).isEqualTo( "GRU - BRC - SCL - ORL - CDG > $40" );
	}

	@Test(expected = InvalidInputException.class)
	@DisplayName( "should ignore duplicate route" )
	public void shouldIgnoreDuplicateRoute() {
		String routeInput = "GRU-GRU";

		routeService.findBestRoute( routeInput, ROUTES );
	}

	@Test(expected = InvalidInputException.class)
	@DisplayName( "should ignore empty route" )
	public void shouldIgnoreEmptyRoute() {
		String routeInput = "";

		routeService.findBestRoute( routeInput, ROUTES );
	}

	@Test(expected = InvalidInputException.class)
	@DisplayName( "should ignore numeric route" )
	public void shouldIgnoreNumericRoute() {
		String routeInput = "G1R-CDG";

		routeService.findBestRoute( routeInput, ROUTES );
	}

	@Test(expected = NoSuchElementException.class)
	@DisplayName( "should ignore numeric route" )
	public void shouldIgnoreUnknownRoute() {
		String routeInput = "GRU-XXX";

		routeService.findBestRoute( routeInput, ROUTES );
	}
}
