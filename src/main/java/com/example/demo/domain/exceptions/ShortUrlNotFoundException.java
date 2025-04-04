package com.example.demo.domain.exceptions;

public class ShortUrlNotFoundException extends RuntimeException{

	private static final long serialVersionUID = 1L;

	
	public ShortUrlNotFoundException(String message) {
		super(message);
	}
}
