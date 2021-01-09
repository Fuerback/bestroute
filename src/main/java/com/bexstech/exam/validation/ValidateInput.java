package com.bexstech.exam.validation;

import com.bexstech.exam.dto.RouteDTO;
import com.bexstech.exam.exception.BadRequestException;

public class ValidateInput {

	public static void isValid(String route) {
		isNotNullOrEmpty( route );
		hasHyphen( route );
		hasSevenDigits( route );
		isValid( new RouteDTO( route.split( "-" )[0], route.split( "-" )[1], 1 ) );
	}

	public static void isValid(RouteDTO routeDTOInput) {
		isNotNull( routeDTOInput );
		isNotEmpty( routeDTOInput );
		isNotSameRoute( routeDTOInput );
		sizeIsThree( routeDTOInput );
		isOnlyLetters( routeDTOInput );
		isValidPrice( routeDTOInput );
	}

	private static void hasSevenDigits(String route) {
		if(!(route.length() == 7)) {
			throw new BadRequestException( "has to have 7 digits" );
		}
	}

	private static void hasHyphen(String route) {
		if(!route.contains( "-" )) {
			throw new BadRequestException( "has to have hyphen to separate the route" );
		}
	}

	private static void isNotNullOrEmpty(String route) {
		if(route == null &&	route.isEmpty()) {
			throw new BadRequestException( "the route can not be null or empty" );
		}
	}

	private static void isValidPrice(RouteDTO routeDTOInput) {
		if(routeDTOInput.getPrice() == null || routeDTOInput.getPrice() <= 0) {
			throw new BadRequestException( "the price can not be null, zero or negative" );
		}
	}

	private static void isOnlyLetters(RouteDTO routeDTOInput) {
		if(!routeDTOInput.getSource().matches( "^[a-zA-Z]*$" ) ||
				!routeDTOInput.getDestination().matches( "^[a-zA-Z]*$" )) {
			throw new BadRequestException( "route can not contain numbers or special characters" );
		}
	}

	private static void sizeIsThree(RouteDTO routeDTOInput) {
		if(!(routeDTOInput.getSource().length() == 3) ||
				!(routeDTOInput.getDestination().length() == 3)) {
			throw new BadRequestException( "each initials has to contain 3 letters" );
		}
	}

	private static void isNotSameRoute(RouteDTO routeDTOInput) {
		if(routeDTOInput.getSource().equalsIgnoreCase( routeDTOInput.getDestination() )) {
			throw new BadRequestException( "the source can not be the same as destination" );
		}
	}

	private static void isNotEmpty(RouteDTO routeDTOInput) {
		if(routeDTOInput.getSource().isEmpty() ||
				routeDTOInput.getDestination().isEmpty()) {
			throw new BadRequestException( "the source or destination can not be empty" );
		}
	}

	private static void isNotNull(RouteDTO routeDTOInput) {
		if(routeDTOInput.getDestination() == null ||
				routeDTOInput.getSource() == null) {
			throw new BadRequestException( "the source or destination can not be null" );
		}
	}
}
