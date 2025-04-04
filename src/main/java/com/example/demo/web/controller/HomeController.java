package com.example.demo.web.controller;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.example.demo.domain.model.CreateShortUrlCmd;
import com.example.demo.domain.model.ShortUrlDto;
import com.example.demo.services.ShortUrlService;
import com.example.demo.web.dto.CreateShortUrlForm;

import jakarta.validation.Valid;

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
		model.addAttribute("createShortUrlForm", new CreateShortUrlForm(""));
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
			CreateShortUrlCmd cmd = new CreateShortUrlCmd(form.originalUrl());
			var shortUrlDto = shortUrlService.createShortUrl(cmd);
			redirectAttributes.addFlashAttribute("successMessage", "Short URL created: " + "http://localhost:8080/s/"+shortUrlDto.shortkey());
		}catch(Exception e) {
			redirectAttributes.addFlashAttribute("errorMessage", "Failed to create short url.");
		}
		
		return "redirect:/";
	}
	
}
