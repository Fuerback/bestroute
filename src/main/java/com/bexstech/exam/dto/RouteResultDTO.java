package com.bexstech.exam.dto;

public class RouteResultDTO {

	private String routeDescription = "";
	private Integer totalPrice = 0;

	public RouteResultDTO(String routeDescription, Integer totalPrice) {
		this.routeDescription = routeDescription;
		this.totalPrice = totalPrice;
	}

	@Override public String toString() {
		return routeDescription + " > $" + totalPrice;
	}
}
