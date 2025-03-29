package com.example.demo.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.example.demo.domain.entity.ShortUrl;
import com.example.demo.domain.repositories.ShortUrlRepository;

@Service
public class ShortUrlService {

	private final ShortUrlRepository shortUrlRepository;
	
	public ShortUrlService(ShortUrlRepository shortUrlRepository) {
		this.shortUrlRepository = shortUrlRepository;
	}
	
	
	public List<ShortUrl> findAllPublicUrls(){
		return shortUrlRepository.findAllPublicUrls();
	}
}
