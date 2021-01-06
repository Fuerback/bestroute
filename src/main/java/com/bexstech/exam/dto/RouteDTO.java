package com.bexstech.exam.dto;

public class RouteDTO {
	private String from;
	private String to;
	private Integer price;

	public RouteDTO(String from, String to, Integer price) {
		this.from = from;
		this.to = to;
		this.price = price;
	}

	public String getFrom() {
		return from.toUpperCase();
	}

	public String getTo() {
		return to.toUpperCase();
	}

	public Integer getPrice() {
		return price;
	}

	public static RouteDTO from(String routeInput) {
		String[] route = routeInput.split("-");
		return new RouteDTO( route[0], route[1], null );
	}

	@Override public String toString() {
		return this.from + " - " + this.to + " > $" + this.price;
	}
}
