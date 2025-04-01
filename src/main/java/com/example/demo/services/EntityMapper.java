package com.example.demo.services;

import org.springframework.stereotype.Component;

import com.example.demo.domain.entity.ShortUrl;
import com.example.demo.domain.entity.User;
import com.example.demo.domain.model.ShortUrlDto;
import com.example.demo.domain.model.UserDto;

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
