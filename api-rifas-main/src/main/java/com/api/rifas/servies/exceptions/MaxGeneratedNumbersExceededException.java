package com.api.rifas.servies.exceptions;

public class MaxGeneratedNumbersExceededException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public MaxGeneratedNumbersExceededException(String message) {
		super(message);
	}
}
