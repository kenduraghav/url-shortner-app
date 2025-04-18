package com.example.demo.domain.repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.example.demo.domain.entities.ShortUrl;

@Repository
public interface ShortUrlRepository extends JpaRepository<ShortUrl, Long> {

	@Query("select e from ShortUrl e where e.isPrivate = false")
	@EntityGraph(attributePaths = {"createdBy"})
	Page<ShortUrl> findAllPublicUrls(Pageable page);

	boolean existsByShortkey(String shortKey);
	
	@Query("select e from ShortUrl e left join fetch e.createdBy where e.shortkey = ?1 order by e.createdAt desc")
	Optional<ShortUrl> findByShortkey(String shortKey);
	
	
	Page<ShortUrl>  findByCreatedById(Long userId, Pageable page);
	
	@Modifying
	void deleteByIdInAndCreatedById(List<Long> ids, Long currentUserId);

	@Query("select e from ShortUrl e left join fetch e.createdBy order by e.createdAt desc")
	Page<ShortUrl> findAllShortUrls(Pageable pageable);
}
