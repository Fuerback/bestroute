package com.bexstech.exam.validation;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import com.bexstech.exam.dto.RouteDTO;

public class ValidateInputTest {

	@Test
	@DisplayName("should validate text input")
	void shouldValidateTextInput() {
		assertTrue( ValidateInput.isValid( "GRU-FLN" ) );
		assertTrue( ValidateInput.isValid( "GRU-fln" ) );
		assertTrue( ValidateInput.isValid( "gru-fln" ) );
		assertTrue( ValidateInput.isValid( "xxx-zzz" ) );
	}

	@Test
	@DisplayName("should validate object input")
	void shouldValidateObjectInput() {
		assertTrue( ValidateInput.isValid( new RouteDTO( "GRU", "FLN", 0 ) ) );
		assertTrue( ValidateInput.isValid( new RouteDTO( "GRU", "fln", 1 ) ) );
		assertTrue( ValidateInput.isValid( new RouteDTO( "gru", "fln", 2 ) ) );
		assertTrue( ValidateInput.isValid( new RouteDTO( "xxx", "zzz", 50 ) ) );
	}

	@Test
	@DisplayName("should invalidate text input")
	void shouldInvalidateTextInput() {
		assertFalse( ValidateInput.isValid( "GRU-GRU" ) );
		assertFalse( ValidateInput.isValid( "xxx-xxx" ) );
		assertFalse( ValidateInput.isValid( "gr1-fln" ) );
		assertFalse( ValidateInput.isValid( "gru-fl" ) );
		assertFalse( ValidateInput.isValid( "" ) );
		assertFalse( ValidateInput.isValid( "gru fln" ) );
	}

	@Test
	@DisplayName("should invalidate object input")
	void shouldInvalidateObjectInput() {
		assertFalse( ValidateInput.isValid( new RouteDTO( "GRU", "GRU", 0 ) ) );
		assertFalse( ValidateInput.isValid( new RouteDTO( "xxx", "xxx", 0 ) ) );
		assertFalse( ValidateInput.isValid( new RouteDTO( "GR1", "FLN", 0 ) ) );
		assertFalse( ValidateInput.isValid( new RouteDTO( "GRU", "FL", 0 ) ) );
		assertFalse( ValidateInput.isValid( new RouteDTO( "", "FLN", 0 ) ) );
		assertFalse( ValidateInput.isValid( new RouteDTO( "GRU", "FLN", -1 ) ) );
		assertFalse( ValidateInput.isValid( new RouteDTO( "GRU", "FLN", null ) ) );
		assertFalse( ValidateInput.isValid( new RouteDTO( null, null, null ) ) );
	}
}
