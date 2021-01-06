package com.bexstech.exam.service;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Arrays;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.junit.MockitoJUnitRunner;

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
		RouteModel routeModel = new RouteModel( "GRU", "CDG", null );

		String bestRoute = routeService.findBestRoute( routeModel, ROUTES );

		assertThat( bestRoute ).isEqualTo( "GRU - BRC - SCL - ORL - CDG > $40" );
	}

//	@Test
//	public void shouldIgnoreInvalidRoute() {
//		RouteModel routeModel = new RouteModel( "GRU", "GRU", null );
//
//		String bestRoute = routeService.findBestRoute( routeModel, ROUTES );
//
//		assertThat( bestRoute ).isEqualTo( "GRU - BRC - SCL - ORL - CDG > $40" );
//	}
}
