package com.example.demo.services;

import java.util.List;

import org.springframework.stereotype.Service;

import com.example.demo.domain.entity.ShortUrl;
import com.example.demo.domain.model.ShortUrlDto;
import com.example.demo.domain.repositories.ShortUrlRepository;

@Service
public class ShortUrlService {

	private final ShortUrlRepository shortUrlRepository;
	private final EntityMapper entityMapper;
	
	public ShortUrlService(ShortUrlRepository shortUrlRepository, EntityMapper entityMapper) {
		this.shortUrlRepository = shortUrlRepository;
		this.entityMapper = entityMapper;
	}
	
	
	public List<ShortUrlDto> findAllPublicUrls(){
		return shortUrlRepository.findAllPublicUrls()
				.stream()
				.map(entityMapper::toShortUrlDto)
				.toList();
	}
}
