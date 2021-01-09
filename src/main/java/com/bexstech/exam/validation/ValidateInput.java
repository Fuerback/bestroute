package com.bexstech.exam.validation;

import com.bexstech.exam.dto.RouteDTO;

public class ValidateInput {

	public static boolean isValid(String route) {
		return route != null &&
				!route.isEmpty() &&
				route.contains( "-" ) &&
				route.length() == 7 &&
				isValid( new RouteDTO( route.split( "-" )[0], route.split( "-" )[1], 0 ) );

	}

	public static boolean isValid(RouteDTO routeDTOInput) {
		return isNotNull( routeDTOInput ) &&
				isNotEmpty( routeDTOInput ) &&
				isNotSameRoute( routeDTOInput ) &&
				sizeIsThree( routeDTOInput ) &&
				isOnlyLetters( routeDTOInput ) &&
				isValidPrice( routeDTOInput );

	}

	private static boolean isValidPrice(RouteDTO routeDTOInput) {
		return routeDTOInput.getPrice() != null &&
				routeDTOInput.getPrice() >= 0;
	}

	private static boolean isOnlyLetters(RouteDTO routeDTOInput) {
		return routeDTOInput.getSource().matches( "^[a-zA-Z]*$" ) &&
				routeDTOInput.getDestination().matches( "^[a-zA-Z]*$" );
	}

	private static boolean sizeIsThree(RouteDTO routeDTOInput) {
		return routeDTOInput.getSource().length() == 3 &&
				routeDTOInput.getDestination().length() == 3;
	}

	private static boolean isNotSameRoute(RouteDTO routeDTOInput) {
		return !routeDTOInput.getSource().equalsIgnoreCase( routeDTOInput.getDestination() );
	}

	private static boolean isNotEmpty(RouteDTO routeDTOInput) {
		return !routeDTOInput.getSource().isEmpty() &&
				!routeDTOInput.getDestination().isEmpty();
	}

	private static boolean isNotNull(RouteDTO routeDTOInput) {
		return routeDTOInput.getDestination() != null &&
				routeDTOInput.getSource() != null;
	}
}
