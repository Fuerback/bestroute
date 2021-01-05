package com.bexstech.exam.services;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.bexstech.exam.dto.RouteResultDTO;
import com.bexstech.exam.models.RouteModel;

@Service
public class RouteService {

	private String routeDescription = "";
	private Integer totalPrice = 0;
	private List<RouteResultDTO> routeResultDTOS = new ArrayList();

	public String findBestRoute(String startingPoint, String destination, List<RouteModel> routeModels) {
		List<RouteModel> staringPoints = findStaringPoints( startingPoint, routeModels );

		staringPoints.forEach( currentRoute -> buildRoute(currentRoute, destination, routeModels) );

//		staringPoints.stream()
//				.map( currentRoute -> buildRoute(currentRoute, destination, routeModels) )
//				.filter( bestRoute -> !bestRoute.isEmpty() )
//				.collect( Collectors.toList());

		return routeDescription + " > $" + totalPrice;
	}

	private List<RouteModel> findStaringPoints(String startingPoint, List<RouteModel> routeModels) {
		return routeModels.stream()
				.filter( routeModel -> routeModel.getFrom().equalsIgnoreCase( startingPoint ) )
				.collect( Collectors.toList() );
	}

	private void buildRoute(RouteModel routeModel, String destination, List<RouteModel> routeModels) {
		//Map<Integer, String> routes = new HashMap();
		//routes.put( routeModel.getPrice(), routeModel.getFrom() + " - " + routeModel.getTo() );

		routeDescription = routeModel.getFrom() + " - " + routeModel.getTo();
		totalPrice += routeModel.getPrice();
		String currentDestination = routeModel.getTo();
		while( !currentDestination.equalsIgnoreCase( destination ) ) {
			List<RouteModel> staringPoints = findStaringPoints( routeModel.getTo(), routeModels );
			staringPoints.forEach( currentRoute -> buildRoute(currentRoute, destination, routeModels) );
		}

		routeResultDTOS.add( new RouteResultDTO(routeDescription, totalPrice) );
	}
}
