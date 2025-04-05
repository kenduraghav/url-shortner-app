package com.example.demo.web.controller;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.example.demo.domain.entities.User;
import com.example.demo.domain.repositories.UsersRepository;

@Service
public class SecurityUtils {
	
	
	private final UsersRepository userRepository;
	
	public SecurityUtils(UsersRepository userRepository) {
		this.userRepository = userRepository;
	}

	public User getCurrentUser() {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		
		if(authentication != null && authentication.isAuthenticated()) {
			String email = authentication.getName();
			return userRepository.findByEmail(email).orElse(null);
		}
		return null;
	}
}
