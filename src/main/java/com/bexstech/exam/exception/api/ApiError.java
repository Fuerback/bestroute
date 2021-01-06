package com.bexstech.exam.exception.api;

public class ApiError {
	private final String detail;

	public ApiError(String detail) {
		this.detail = detail;
	}

	public String getDetail() {
		return detail;
	}
}
