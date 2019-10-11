package com.mortgage.mortgage.exception;

public class InvalidCustomerException extends RuntimeException{
	
	private static final long serialVersionUID = 1L;

	public InvalidCustomerException(String message) {
		super(message);
	}
}
