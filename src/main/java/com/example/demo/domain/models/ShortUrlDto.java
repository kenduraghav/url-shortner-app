package com.example.demo.domain.models;

import java.io.Serializable;
import java.time.Instant;
import java.time.LocalDateTime;

public record ShortUrlDto(Long id, String shortkey, 
		String originalUrl, UserDto createdBy, 
		boolean isPrivate, 
		LocalDateTime createdAt,
		Instant expiresAt,
		long clickCount) implements Serializable {

}
