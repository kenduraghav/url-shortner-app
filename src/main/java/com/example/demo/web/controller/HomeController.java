package com.example.demo.web.controller;

import java.util.List;

import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.example.demo.domain.entity.ShortUrl;
import com.example.demo.domain.repositories.ShortUrlRepository;
import com.example.demo.service.ShortUrlService;

@Controller
public class HomeController {

	
	private final ShortUrlService shortUrlService;
	
	
	public HomeController(ShortUrlService shortUrlService) {
		this.shortUrlService = shortUrlService;
	}

	@GetMapping("/")
	public String home(Model model) {
		List<ShortUrl> shortUrls = shortUrlService.findAllPublicUrls();
		model.addAttribute("shortUrls", shortUrls);
		model.addAttribute("title", "URL Shortner App - using Thymeleaf");
		return "index";
	}
	
}
