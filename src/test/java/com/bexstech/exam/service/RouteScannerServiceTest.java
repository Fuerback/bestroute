package com.bexstech.exam.service;

import static org.junit.Assert.assertEquals;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.PrintStream;
import java.util.Arrays;
import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.DisplayName;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.junit.MockitoJUnitRunner;

import com.bexstech.exam.dto.RouteDTO;
import com.bexstech.exam.singleton.RouteSingleton;

@RunWith(MockitoJUnitRunner.class)
public class RouteScannerServiceTest {

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
	private RouteScannerService routeScannerService;

	private final InputStream systemIn = System.in;

	private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();

	private ByteArrayInputStream testIn;

	@Before
	public void prepareTests() {
		System.setOut(new PrintStream(outContent));
		RouteSingleton.getInstance().updateRoutes( ROUTES );
	}

	@After
	public void restoreSystemInputOutput() {
		System.setIn(systemIn);
	}

	@Test
	@DisplayName( "should scan valid input text" )
	public void shouldScanValidInputText() {
		final String testString = "GRU-FLN\nexit";
		provideInput(testString);

		routeScannerService.scan( ROUTES );

		assertEquals("please enter the route: best route: GRU - FLN > $25\n"
				+ "please enter the route: exit\n", outContent.toString());
	}

	@Test
	@DisplayName( "should scan invalid input text" )
	public void shouldScanInvalidInputText() {
		final String testString = "GRU-FL1\nexit";
		provideInput(testString);

		routeScannerService.scan( ROUTES );

		assertEquals("please enter the route: invalid input\n"
				+ "please enter the route: exit\n", outContent.toString());
	}

	private void provideInput(String data) {
		testIn = new ByteArrayInputStream(data.getBytes());
		System.setIn(testIn);
	}
}
