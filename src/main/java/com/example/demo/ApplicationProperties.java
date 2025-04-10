package com.example.demo;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.bind.DefaultValue;
import org.springframework.validation.annotation.Validated;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;

@ConfigurationProperties(prefix = "app")
@Validated
public record ApplicationProperties(
		@NotBlank
		@DefaultValue("http://localhost:8080/")
		String baseUrl,
		
		
		@DefaultValue("30")
		@Min(1)
		@Max(365)
		int defaultExpiryInDays,
		
		@DefaultValue("true")
		boolean validateURL,
		
		@DefaultValue("10")
		Integer pageSize
		) {
	
}
