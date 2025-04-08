package com.example.demo.web.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.example.demo.ApplicationProperties;
import com.example.demo.domain.exceptions.ShortUrlNotFoundException;
import com.example.demo.domain.models.CreateShortUrlCmd;
import com.example.demo.domain.models.ShortUrlDto;
import com.example.demo.domain.services.ShortUrlService;
import com.example.demo.web.dto.CreateShortUrlForm;

import jakarta.validation.Valid;

@Controller
public class HomeController {

	private final ShortUrlService shortUrlService;
	private final ApplicationProperties properties;
	private final SecurityUtils securityUtils;
	
	public HomeController(ShortUrlService shortUrlService, ApplicationProperties properties, SecurityUtils securityUtils) {
		this.shortUrlService = shortUrlService;
		this.properties = properties;
		this.securityUtils = securityUtils;
	}

	@GetMapping("/")
	public String home(Model model) {
		List<ShortUrlDto> shortUrls = shortUrlService.findAllPublicUrls();
		model.addAttribute("shortUrls", shortUrls);
		model.addAttribute("title", "URL Shortner App - using Thymeleaf");
		model.addAttribute("createShortUrlForm", new CreateShortUrlForm("", null, null));
		return "index";
	}
	
	@PostMapping("/short-urls")
	public String createShortUrls(@ModelAttribute("createShortUrlForm") @Valid CreateShortUrlForm form, 
			BindingResult bindingResult,
			RedirectAttributes redirectAttributes,
			Model model) {
		if(bindingResult.hasErrors()) {
			List<ShortUrlDto> shortUrls = shortUrlService.findAllPublicUrls();
			model.addAttribute("shortUrls", shortUrls);
			return "index";
		}
		try {
			Long currentUserId = securityUtils.getCurrentUserId();
			CreateShortUrlCmd cmd = new CreateShortUrlCmd(form.originalUrl(), 
					form.isPrivate(), currentUserId,
					form.expirationInDays());
			var shortUrlDto = shortUrlService.createShortUrl(cmd);
			redirectAttributes.addFlashAttribute("successMessage", "Short URL created: " + properties.baseUrl()+"/s/"+shortUrlDto.shortkey());
		}catch(Exception e) {
			redirectAttributes.addFlashAttribute("errorMessage", "Failed to create short url.");
		}
		return "redirect:/";
	}
	
	
	@GetMapping("/s/{shortKey}")
	public String redirectToOriginalURL(@PathVariable String shortKey) {
		Long currentUserId = securityUtils.getCurrentUserId();
		Optional<ShortUrlDto> shortUrlOptional = shortUrlService.accessShortUrl(shortKey,currentUserId);
		
		if(shortUrlOptional.isEmpty()) {
			throw new ShortUrlNotFoundException("Invalid Short URL:" + shortKey);
		}
		
		ShortUrlDto shortUrlDto = shortUrlOptional.get();
		return "redirect:"+shortUrlDto.originalUrl();
	}
	
	@GetMapping("/login")
	String loginForm() {
		return "login";
	}
}
