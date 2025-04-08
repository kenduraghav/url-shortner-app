package com.example.demo.web.dto;

import org.springframework.boot.context.properties.bind.DefaultValue;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;

public record CreateShortUrlForm(
		@NotBlank(message = "Original URL is required")
		String originalUrl, 
		@DefaultValue("false")
		Boolean isPrivate,
		@Min(1)
		@Max(365)
		Integer expirationInDays) {

}
