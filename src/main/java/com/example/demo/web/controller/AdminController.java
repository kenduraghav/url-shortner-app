package com.example.demo.web.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.demo.ApplicationProperties;
import com.example.demo.domain.models.PagedResult;
import com.example.demo.domain.models.ShortUrlDto;
import com.example.demo.domain.services.ShortUrlService;

@Controller
@RequestMapping("/admin")
public class AdminController {

	
	private final ShortUrlService shortUrlService;
	private final ApplicationProperties properties;
	
	
	public AdminController(ShortUrlService shortUrlService, ApplicationProperties properties) {
		this.shortUrlService = shortUrlService;
		this.properties = properties;
	}
	
	
	
	@GetMapping("/dashboard")
	public String dashboard(@RequestParam(defaultValue = "1") int page, Model model) {
		PagedResult<ShortUrlDto> shortUrls = shortUrlService.findAllShortUrls(page,properties.pageSize());
		model.addAttribute("shortUrls", shortUrls);
		model.addAttribute("paginationUrl","/admin/dashboard");
		return "admin-dashboard";
	}
}
