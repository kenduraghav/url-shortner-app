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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.example.demo.ApplicationProperties;
import com.example.demo.domain.exceptions.ShortUrlNotFoundException;
import com.example.demo.domain.models.CreateShortUrlCmd;
import com.example.demo.domain.models.PagedResult;
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
	public String home(Model model,
			@RequestParam(defaultValue = "1") int page) {
		PagedResult<ShortUrlDto> shortUrls = shortUrlService.findAllPublicUrls(page,properties.pageSize());
		model.addAttribute("shortUrls", shortUrls);
		model.addAttribute("paginationUrl","/");
		model.addAttribute("createShortUrlForm", new CreateShortUrlForm("", null, null));
		return "index";
	}
	
	@GetMapping("/my-urls")
	public String getUserUrls(@RequestParam(defaultValue="1") int page, Model model) {
		Long currentUserId = securityUtils.getCurrentUserId();
		PagedResult<ShortUrlDto> userShortURLs = shortUrlService.getUserShortURLs(currentUserId, page, properties.pageSize());
		model.addAttribute("paginationUrl","/my-urls");
		model.addAttribute("shortUrls", userShortURLs);
		return "my-urls";
	}
	
	@PostMapping("/short-urls")
	public String createShortUrls(@ModelAttribute("createShortUrlForm") @Valid CreateShortUrlForm form, 
			BindingResult bindingResult,
			RedirectAttributes redirectAttributes,
			Model model) {
		if(bindingResult.hasErrors()) {
			PagedResult<ShortUrlDto> shortUrls = shortUrlService.findAllPublicUrls(1,properties.pageSize());
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
	
	@PostMapping("/delete-urls")
	public String deleteShortUrls(@RequestParam List<Long> ids, RedirectAttributes redirectAttributes, Model  model) {
		if(ids.isEmpty()) {
			redirectAttributes.addFlashAttribute("errorMessage", "Please select atleast one record to delete.");
			return "my-urls";
		}
		Long currentUserId = securityUtils.getCurrentUserId();
		shortUrlService.deleteShortUrlsByIds(ids,currentUserId);
		redirectAttributes.addFlashAttribute("successMessage", "Selected URLs have been deleted");
		return "redirect:/my-urls";
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
