package com.bexstech.exam.dto;

public class RouteDTO {
	private String source;
	private String destination;
	private Integer price;

	public RouteDTO(String source, String destination, Integer price) {
		this.source = source;
		this.destination = destination;
		this.price = price;
	}

	public String getSource() {
		if(source != null) {
			return source.toUpperCase();
		}
		return null;
	}

	public String getDestination() {
		if(destination != null) {
			return destination.toUpperCase();
		}
		return null;
	}

	public Integer getPrice() {
		return price;
	}

	public static RouteDTO from(String routeInput) {
		String[] route = routeInput.split("-");
		return new RouteDTO( route[0], route[1], 0 );
	}

	@Override public String toString() {
		return this.source + " - " + this.destination + " > $" + this.price;
	}
}
