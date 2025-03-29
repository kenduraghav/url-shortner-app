package com.example.demo.domain.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.example.demo.domain.entity.ShortUrl;

@Repository
public interface ShortUrlRepository extends JpaRepository<ShortUrl, Long> {

	@Query("select e from ShortUrl e where e.isPrivate = false order by e.createdAt desc")
	List<ShortUrl> findAllPublicUrls();
}
