package com.example.demo.web.dto;

import jakarta.validation.constraints.NotBlank;

public record CreateShortUrlForm(
		@NotBlank(message = "Original URL is required")
		String originalUrl) {

}
