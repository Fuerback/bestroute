package com.bexstech.exam.util;

import com.bexstech.exam.models.RouteModel;

public class ValidateInput {

	public static boolean isValid(String route) {
		return route != null &&
				!route.isEmpty() &&
				route.contains( "-" ) &&
				route.length() == 7 &&
				isValid( new RouteModel( route.split( "-" )[0], route.split( "-" )[1], null ) );

	}

	private static boolean isValid(RouteModel routeModelInput) {
		return isNotNull( routeModelInput ) &&
				isNotEmpty( routeModelInput ) &&
				isNotSameRoute( routeModelInput ) &&
				sizeIsThree( routeModelInput ) &&
				isOnlyLetters( routeModelInput );

	}

	private static boolean isOnlyLetters(RouteModel routeModelInput) {
		return routeModelInput.getFrom().matches( "^[a-zA-Z]*$" ) &&
				routeModelInput.getTo().matches( "^[a-zA-Z]*$" );
	}

	private static boolean sizeIsThree(RouteModel routeModelInput) {
		return routeModelInput.getFrom().length() == 3 &&
				routeModelInput.getTo().length() == 3;
	}

	private static boolean isNotSameRoute(RouteModel routeModelInput) {
		return !routeModelInput.getFrom().equalsIgnoreCase( routeModelInput.getTo() );
	}

	private static boolean isNotEmpty(RouteModel routeModelInput) {
		return !routeModelInput.getFrom().isEmpty() &&
				!routeModelInput.getTo().isEmpty();
	}

	private static boolean isNotNull(RouteModel routeModelInput) {
		return routeModelInput.getTo() != null &&
				routeModelInput.getFrom() != null;
	}
}
