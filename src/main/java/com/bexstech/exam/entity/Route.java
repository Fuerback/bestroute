package com.bexstech.exam.entity;

public class Route {
	private String from;
	private String to;
	private Integer price;

	public Route(String from, String to, Integer price) {
		this.from = from;
		this.to = to;
		this.price = price;
	}

	public String getFrom() {
		return from;
	}

	public String getTo() {
		return to;
	}

	public Integer getPrice() {
		return price;
	}

	@Override public String toString() {
		return this.from + " - " + this.to + " > $" + this.price;
	}
}
