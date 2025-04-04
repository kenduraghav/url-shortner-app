package com.example.demo.domain.services;

import org.springframework.stereotype.Component;

import com.example.demo.domain.entities.ShortUrl;
import com.example.demo.domain.entities.User;
import com.example.demo.domain.models.ShortUrlDto;
import com.example.demo.domain.models.UserDto;

@Component
public class EntityMapper {

	
	public ShortUrlDto toShortUrlDto(ShortUrl shortUrl) {
		
		UserDto createdBy = null;
		
		if(shortUrl.getCreatedBy() != null) {
			createdBy = toUserDto(shortUrl.getCreatedBy());
		}
		
		return new ShortUrlDto(shortUrl.getId(), 
				shortUrl.getShortkey(), 
				shortUrl.getOriginalUrl(), 
				createdBy,
				shortUrl.getIsPrivate(), 
				shortUrl.getCreatedAt(), 
				shortUrl.getExpiresAt(), 
				shortUrl.getClickCount());
	}

	private UserDto toUserDto(User user) {
		return new UserDto(user.getId(), user.getName());
	}
}
