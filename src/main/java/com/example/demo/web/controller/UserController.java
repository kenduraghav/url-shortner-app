package com.example.demo.web.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.example.demo.domain.models.RegisterUserCmd;
import com.example.demo.domain.models.Role;
import com.example.demo.domain.services.UserService;
import com.example.demo.web.dto.RegisterUserRequest;

import jakarta.validation.Valid;

@Controller
@RequestMapping("/register")
public class UserController {
	
	private final UserService userService;
	
	public UserController(UserService userService) {
		this.userService = userService;
	}

	
	@GetMapping
	String registerForm(Model model) {
		model.addAttribute("registerUserRequest", new RegisterUserRequest(null, null, null));
		return "register";
	}
	
	
	@PostMapping
	String registerUser(@ModelAttribute("registerUserRequest") @Valid RegisterUserRequest userForm, 
			BindingResult bindingResult,
			RedirectAttributes redirectAttributes,
			Model model) {
		
		if(bindingResult.hasErrors()) {
			return "register";
		}
		
		
		try {
			
			var user = new RegisterUserCmd(userForm.email(), 
					userForm.password(), userForm.name(), 
					Role.ROLE_USER);
			userService.save(user);
			redirectAttributes.addFlashAttribute("successMessage", "User Registered successfully.!");
			return "redirect:/login";
		}catch(Exception e) {
			redirectAttributes.addFlashAttribute("errorMessage", "Failed to register user.");
			return "redirect:/register";
		}
		
		
	}
}
