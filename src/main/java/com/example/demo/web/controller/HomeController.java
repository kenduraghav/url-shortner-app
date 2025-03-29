package com.example.demo.web.controller;

import java.util.List;

import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.example.demo.domain.entity.ShortUrl;
import com.example.demo.domain.repositories.ShortUrlRepository;

@Controller
public class HomeController {

	
	private final ShortUrlRepository shortUrlRepository;
	
	
	public HomeController(ShortUrlRepository shortUrlRepository) {
		this.shortUrlRepository = shortUrlRepository;
	}

	@GetMapping("/")
	public String home(Model model) {
		
		List<ShortUrl> shortUrls = shortUrlRepository.findAll(Sort.by(Direction.DESC, "createdAt"));
		model.addAttribute("shortUrls", shortUrls);
		model.addAttribute("title", "URL Shortner App - using Thymeleaf");
		return "index";
	}
	
}
