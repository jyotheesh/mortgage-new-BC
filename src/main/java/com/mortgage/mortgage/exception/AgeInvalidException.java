package com.mortgage.mortgage.exception;

public class AgeInvalidException extends RuntimeException{
	
	private static final long serialVersionUID = 1L;

	public AgeInvalidException(String message) {
		super(message);
	}

}
