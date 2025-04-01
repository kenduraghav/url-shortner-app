package com.example.demo.web.controller;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.example.demo.domain.entity.ShortUrl;
import com.example.demo.domain.model.ShortUrlDto;
import com.example.demo.services.ShortUrlService;

@Controller
public class HomeController {

	
	private final ShortUrlService shortUrlService;
	
	
	public HomeController(ShortUrlService shortUrlService) {
		this.shortUrlService = shortUrlService;
	}

	@GetMapping("/")
	public String home(Model model) {
		List<ShortUrlDto> shortUrls = shortUrlService.findAllPublicUrls();
		model.addAttribute("shortUrls", shortUrls);
		model.addAttribute("title", "URL Shortner App - using Thymeleaf");
		return "index";
	}
	
}
