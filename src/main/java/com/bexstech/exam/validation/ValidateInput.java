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
		return routeDTOInput.getFrom().matches( "^[a-zA-Z]*$" ) &&
				routeDTOInput.getTo().matches( "^[a-zA-Z]*$" );
	}

	private static boolean sizeIsThree(RouteDTO routeDTOInput) {
		return routeDTOInput.getFrom().length() == 3 &&
				routeDTOInput.getTo().length() == 3;
	}

	private static boolean isNotSameRoute(RouteDTO routeDTOInput) {
		return !routeDTOInput.getFrom().equalsIgnoreCase( routeDTOInput.getTo() );
	}

	private static boolean isNotEmpty(RouteDTO routeDTOInput) {
		return !routeDTOInput.getFrom().isEmpty() &&
				!routeDTOInput.getTo().isEmpty();
	}

	private static boolean isNotNull(RouteDTO routeDTOInput) {
		return routeDTOInput.getTo() != null &&
				routeDTOInput.getFrom() != null;
	}
}
