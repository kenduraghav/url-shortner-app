package com.example.demo.domain.services;

import java.security.SecureRandom;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.demo.ApplicationProperties;
import com.example.demo.domain.entities.ShortUrl;
import com.example.demo.domain.models.CreateShortUrlCmd;
import com.example.demo.domain.models.ShortUrlDto;
import com.example.demo.domain.repositories.ShortUrlRepository;

@Service
@Transactional(readOnly = true)
public class ShortUrlService {

	private final ShortUrlRepository shortUrlRepository;
	private final EntityMapper entityMapper;
	private final ApplicationProperties properties;
	
	public ShortUrlService(ShortUrlRepository shortUrlRepository, EntityMapper entityMapper, ApplicationProperties properties) {
		this.shortUrlRepository = shortUrlRepository;
		this.entityMapper = entityMapper;
		this.properties = properties;
	}
	
	public List<ShortUrlDto> findAllPublicUrls(){
		return shortUrlRepository.findAllPublicUrls()
				.stream()
				.map(entityMapper::toShortUrlDto)
				.toList();
	}
	
	@Transactional
	public ShortUrlDto createShortUrl(CreateShortUrlCmd cmd) {
		if(properties.validateURL()) {
			boolean urlExists = URLChecker.isURLExists(cmd.originalUrl());
			if(!urlExists) {
				throw new RuntimeException("Invalid URL");
			}
		}
		
		var shortkey= generateUniqueShortKey();
		var shortUrl = new ShortUrl();
		shortUrl.setShortkey(shortkey);
		shortUrl.setOriginalUrl(cmd.originalUrl());
		shortUrl.setClickCount(0l);
		shortUrl.setCreatedBy(null);
		shortUrl.setExpiresAt(Instant.now().plus(properties.defaultExpiryInDays(), ChronoUnit.DAYS));
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

    @Transactional
	public Optional<ShortUrlDto> accessShortUrl(String shortKey) {
		Optional<ShortUrl> optionalShortUrl = shortUrlRepository.findByShortkey(shortKey);
		if(optionalShortUrl.isEmpty()) {
			return Optional.empty();
		}
		
		ShortUrl shortUrl = optionalShortUrl.get();
		if(shortUrl.getExpiresAt() != null && shortUrl.getExpiresAt().isBefore(Instant.now())) {
			return Optional.empty();
		}
		
		shortUrl.setClickCount(shortUrl.getClickCount()+ 1);
		shortUrlRepository.save(shortUrl);
		return optionalShortUrl.map(entityMapper::toShortUrlDto);
	}
}
