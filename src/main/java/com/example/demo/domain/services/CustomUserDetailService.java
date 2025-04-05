package com.example.demo.domain.services;


import java.util.List;

import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.example.demo.domain.entities.User;
import com.example.demo.domain.repositories.UsersRepository;

@Service
public class CustomUserDetailService implements UserDetailsService{
	
	private final UsersRepository userRepository;
	
	
	public CustomUserDetailService(UsersRepository userRepository) {
		this.userRepository = userRepository;
	}
	

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		
		User user = userRepository.findByEmail(username)
				.orElseThrow(
						() -> new UsernameNotFoundException("Invalid Username or Password")
					);
		
		return new org.springframework.security.core.userdetails.User(
				user.getEmail(), 
				user.getPassword(), 
				List.of(new SimpleGrantedAuthority(user.getRole().name())));
	}

}
