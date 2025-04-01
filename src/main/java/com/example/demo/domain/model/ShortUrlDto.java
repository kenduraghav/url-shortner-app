package com.example.demo.domain.model;

import java.io.Serializable;
import java.time.LocalDateTime;

public record ShortUrlDto(Long id, String shortkey, 
		String originalUrl, UserDto createdBy, 
		boolean isPrivate, 
		LocalDateTime createdAt,
		LocalDateTime expiresAt,
		long clickCount) implements Serializable {

}
