package com.bexstech.exam.service;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Scanner;

import org.springframework.stereotype.Service;

import com.bexstech.exam.dto.RouteDTO;
import com.bexstech.exam.dto.RouteResponseDTO;
import com.bexstech.exam.exception.BadRequestException;
import com.bexstech.exam.singleton.RouteSingleton;

@Service
public class RouteScannerService {

	private static String EXIT = "exit";

	public RouteScannerService() {
	}

	public void scan(List<RouteDTO> routeDTOS) {
		Scanner scanner = new Scanner(System.in);
		RouteService routeService;
		String route;
		List<RouteDTO> routeModels = RouteSingleton.getInstance().getRouteModels();

		while (!routeModels.isEmpty()) {
			System.out.print("please enter the route: ");

			route = scanner.nextLine();

			if(EXIT.equalsIgnoreCase( route )) {System.out.println(route); break;}

			try {
				routeService = new RouteService();
				RouteResponseDTO routeResponseDTO = routeService.findRoute( route, RouteSingleton.getInstance().getRouteModels() );

				System.out.println(String.format("best route: %s", routeResponseDTO.toString()));
			} catch (BadRequestException e) {
				System.out.println(e.getMessage());
			} catch (NoSuchElementException e) {
				System.out.println("no routes found");
			}
		}
	}
}
