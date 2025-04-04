package com.example.demo;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.example.demo.domain.exceptions.ShortUrlNotFoundException;

@ControllerAdvice
public class GlobalExceptionHandler {

	private static final Logger log = LoggerFactory.getLogger(GlobalExceptionHandler.class);
	
	
	@ExceptionHandler(ShortUrlNotFoundException.class)
	public String handleShortUrlNotFoundException(ShortUrlNotFoundException ex) {
		log.error("Short URL not found: {}", ex.getMessage());
		return "error/404";
	}
}
