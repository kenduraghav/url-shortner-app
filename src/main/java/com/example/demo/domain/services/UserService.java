package com.example.demo.domain.services;

import java.time.LocalDateTime;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.demo.domain.entities.User;
import com.example.demo.domain.models.RegisterUserCmd;
import com.example.demo.domain.repositories.UsersRepository;

@Service
public class UserService {
	
	private final UsersRepository userRepository;
	private final PasswordEncoder passwordEncoder;
	
	public UserService(PasswordEncoder encoder, UsersRepository userRepository) {
		this.userRepository = userRepository;
		this.passwordEncoder = encoder;
	}

	public void save(RegisterUserCmd cmd) {
		
		if(userRepository.existsByEmail(cmd.email())) {
			throw new RuntimeException("Email already exists");
		}
		
		var user = new User();
		user.setEmail(cmd.email());
		user.setPassword(passwordEncoder.encode(cmd.password()));
		user.setName(cmd.name());
		user.setRole(cmd.role());
		user.setCreatedAt(LocalDateTime.now());
		userRepository.save(user);
		
	}

}
