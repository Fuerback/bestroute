package com.bexstech.exam.service;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Arrays;
import java.util.List;

import org.junit.Test;
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
	public void shouldFindBestRoute() {
		String routeInput = "GRU-CDG";

		String bestRoute = routeService.findBestRoute( routeInput, ROUTES );

		assertThat( bestRoute ).isEqualTo( "GRU - BRC - SCL - ORL - CDG > $40" );
	}

	@Test(expected = InvalidInputException.class)
	public void shouldIgnoreSameRoute() {
		String routeInput = "GRU-GRU";

		routeService.findBestRoute( routeInput, ROUTES );
	}
}
