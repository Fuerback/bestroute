package com.bexstech.exam.validation;

import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import com.bexstech.exam.dto.RouteDTO;
import com.bexstech.exam.exception.BadRequestException;

public class ValidateInputTest {

	@Test
	@DisplayName("should validate text input")
	void shouldValidateTextInput() {
		ValidateInput.isValid( "GRU-FLN" );
		ValidateInput.isValid( "GRU-fln" );
		ValidateInput.isValid( "gru-fln" );
		ValidateInput.isValid( "xxx-zzz" );
	}

	@Test
	@DisplayName("should validate object input")
	void shouldValidateObjectInput() {
		ValidateInput.isValid( new RouteDTO( "GRU", "fln", 1 ) );
		ValidateInput.isValid( new RouteDTO( "gru", "fln", 2 ) );
		ValidateInput.isValid( new RouteDTO( "xxx", "zzz", 50 ) );
	}

	@Test
	@DisplayName("should invalidate text input")
	void shouldInvalidateTextInput() {
		assertThrows( BadRequestException.class, () -> {
			ValidateInput.isValid( "GRU-GRU" );
		});
		assertThrows( BadRequestException.class, () -> {
			ValidateInput.isValid( "xxx-xxx" );
		});
		assertThrows( BadRequestException.class, () -> {
			ValidateInput.isValid( "gr1-fln" );
		});
		assertThrows( BadRequestException.class, () -> {
			ValidateInput.isValid( "gru-fl" );
		});
		assertThrows( BadRequestException.class, () -> {
			ValidateInput.isValid( "" );
		});
		assertThrows( BadRequestException.class, () -> {
			ValidateInput.isValid( "gru fln" );
		});
	}

	@Test
	@DisplayName("should invalidate object input")
	void shouldInvalidateObjectInput() {
		assertThrows( BadRequestException.class, () -> {
			ValidateInput.isValid( new RouteDTO( "GRU", "GRU", 0 ) );
		});
		assertThrows( BadRequestException.class, () -> {
			ValidateInput.isValid( new RouteDTO( "xxx", "xxx", 0 ) );
		});
		assertThrows( BadRequestException.class, () -> {
			ValidateInput.isValid( new RouteDTO( "GR1", "FLN", 0 ) );
		});
		assertThrows( BadRequestException.class, () -> {
			ValidateInput.isValid( new RouteDTO( "GRU", "FL", 0 ) );
		});
		assertThrows( BadRequestException.class, () -> {
			ValidateInput.isValid( new RouteDTO( "", "FLN", 0 ) );
		});
		assertThrows( BadRequestException.class, () -> {
			ValidateInput.isValid( new RouteDTO( "GRU", "FLN", -1 ) );
		});
		assertThrows( BadRequestException.class, () -> {
			ValidateInput.isValid( new RouteDTO( "GRU", "FLN", null ) );
		});
		assertThrows( BadRequestException.class, () -> {
			ValidateInput.isValid( new RouteDTO( null, null, null ) );
		});
	}
}
