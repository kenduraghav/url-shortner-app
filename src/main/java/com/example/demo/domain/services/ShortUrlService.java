package com.example.demo.domain.services;

import static org.springframework.data.domain.Sort.by;

import java.security.SecureRandom;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.demo.ApplicationProperties;
import com.example.demo.domain.entities.ShortUrl;
import com.example.demo.domain.models.CreateShortUrlCmd;
import com.example.demo.domain.models.PagedResult;
import com.example.demo.domain.models.ShortUrlDto;
import com.example.demo.domain.repositories.ShortUrlRepository;
import com.example.demo.domain.repositories.UsersRepository;

@Service
@Transactional(readOnly = true)
public class ShortUrlService {

	private final ShortUrlRepository shortUrlRepository;
	private final EntityMapper entityMapper;
	private final ApplicationProperties properties;
	private final UsersRepository userRepository;
	
	public ShortUrlService(ShortUrlRepository shortUrlRepository, EntityMapper entityMapper, 
			ApplicationProperties properties, UsersRepository userRepository) {
		this.shortUrlRepository = shortUrlRepository;
		this.entityMapper = entityMapper;
		this.properties = properties;
		this.userRepository = userRepository;
	}
	
	public PagedResult<ShortUrlDto> findAllPublicUrls(int pageNo, int pageSize){
		Pageable pageable = getPageable(pageNo, pageSize);
		Page<ShortUrlDto> allPublicUrls = shortUrlRepository.findAllPublicUrls(pageable).map(entityMapper::toShortUrlDto);
		return PagedResult.from(allPublicUrls);
	}
	
	public PagedResult<ShortUrlDto> getUserShortURLs(Long userId, int pageNo, int pageSize){
		Pageable pageable = getPageable(pageNo, pageSize);
		Page<ShortUrlDto> userShortUrls = shortUrlRepository.findByCreatedById(userId,pageable).map(entityMapper::toShortUrlDto);
		return PagedResult.from(userShortUrls);
	}
	
	public PagedResult<ShortUrlDto> findAllShortUrls(int pageNo, int pageSize) {
		Pageable pageable = getPageable(pageNo, pageSize);
		Page<ShortUrlDto> allShortUrls = shortUrlRepository.findAllShortUrls(pageable).map(entityMapper::toShortUrlDto);
		return PagedResult.from(allShortUrls);
	}

	private Pageable getPageable(int pageNo, int pageSize) {
		pageNo = pageNo > 1 ? pageNo-1 : 0; 
		return PageRequest.of(pageNo, pageSize, by(Sort.Direction.DESC,"createdAt"));
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
		shortUrl.setClickCount(0L);
		
		if(cmd.createBy() == null) {
			shortUrl.setCreatedBy(null);
			shortUrl.setExpiresAt(Instant.now().plus(properties.defaultExpiryInDays(), ChronoUnit.DAYS));
			shortUrl.setIsPrivate(false);
		} else {
			shortUrl.setCreatedBy(userRepository.findById(cmd.createBy()).orElse(null));
			if(cmd.expirationInDays() != null) {
				shortUrl.setExpiresAt(Instant.now().plus(cmd.expirationInDays(), ChronoUnit.DAYS));
			} else {
				shortUrl.setExpiresAt(null);
			}
			shortUrl.setIsPrivate((cmd.isPrivate() != null) ? true : null);
		}
		shortUrlRepository.save(shortUrl);
		return entityMapper.toShortUrlDto(shortUrl);
	}

    @Transactional
	public Optional<ShortUrlDto> accessShortUrl(String shortKey, Long currentUserId) {
		Optional<ShortUrl> optionalShortUrl = shortUrlRepository.findByShortkey(shortKey);
		if(optionalShortUrl.isEmpty()) {
			return Optional.empty();
		}
		
		ShortUrl shortUrl = optionalShortUrl.get();
		if(shortUrl.getExpiresAt() != null && shortUrl.getExpiresAt().isBefore(Instant.now())) {
			return Optional.empty();
		}
		
		
		if (shortUrl.getIsPrivate() != null && shortUrl.getIsPrivate()
				&& (shortUrl.getCreatedBy() != null && shortUrl.getCreatedBy().getId() != currentUserId)) {
			return Optional.empty();
		}
		
		shortUrl.setClickCount(shortUrl.getClickCount()+ 1);
		shortUrlRepository.save(shortUrl);
		return optionalShortUrl.map(entityMapper::toShortUrlDto);
	}
    
    
    @Transactional
	public void deleteShortUrlsByIds(List<Long> ids,Long currentUserId) {
		shortUrlRepository.deleteByIdInAndCreatedById(ids, currentUserId);
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
