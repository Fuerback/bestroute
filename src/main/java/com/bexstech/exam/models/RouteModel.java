package com.bexstech.exam.models;

public class RouteModel {
	private String from;
	private String to;
	private Integer price;

	public RouteModel(String from, String to, Integer price) {
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

	public static RouteModel from(String routeInput) {
		String[] route = routeInput.split("-");
		return new RouteModel( route[0], route[1], null );
	}

	@Override public String toString() {
		return this.from + " - " + this.to + " > $" + this.price;
	}
}
