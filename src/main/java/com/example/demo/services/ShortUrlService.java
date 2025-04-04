package com.example.demo.services;

import java.security.SecureRandom;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;

import org.springframework.stereotype.Service;

import com.example.demo.domain.entity.ShortUrl;
import com.example.demo.domain.model.CreateShortUrlCmd;
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
	
	public ShortUrlDto createShortUrl(CreateShortUrlCmd cmd) {
		var shortkey= generateUniqueShortKey();
		var shortUrl = new ShortUrl();
		
		shortUrl.setShortkey(shortkey);
		shortUrl.setOriginalUrl(cmd.originalUrl());
		shortUrl.setClickCount(0l);
		shortUrl.setCreatedBy(null);
		shortUrl.setExpiresAt(LocalDateTime.now().plus(30, ChronoUnit.DAYS));
		shortUrl.setIsPrivate(false);
		shortUrlRepository.save(shortUrl);
		return entityMapper.toShortUrlDto(shortUrl);
	}

	private String generateUniqueShortKey() {
		String shortKey;
		do {
			 shortKey = generateRandomShortKey();
		}while(shortUrlRepository.existsByShortkey(shortKey));
		return shortKey;
	}

	
	private static final String CHARACTERS = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
    private static final int LENGTH = 6;
    private static final SecureRandom RANDOM = new SecureRandom();

    static String generateRandomShortKey() {
        StringBuilder key = new StringBuilder(LENGTH);
        for (int i = 0; i < LENGTH; i++) {
            int randomIndex = RANDOM.nextInt(CHARACTERS.length());
            key.append(CHARACTERS.charAt(randomIndex));
        }
        return key.toString();
    }
}
