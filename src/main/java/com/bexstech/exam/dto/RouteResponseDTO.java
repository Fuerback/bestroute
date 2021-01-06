package com.bexstech.exam.dto;

public class RouteResponseDTO {

	private String routeDescription;
	private Integer totalPrice;

	public RouteResponseDTO(String routeDescription, Integer totalPrice) {
		this.routeDescription = routeDescription;
		this.totalPrice = totalPrice;
	}

	public Integer getTotalPrice() {
		return totalPrice;
	}

	public String getRouteDescription() {
		return routeDescription;
	}

	@Override public String toString() {
		return routeDescription + " > $" + totalPrice;
	}
}
