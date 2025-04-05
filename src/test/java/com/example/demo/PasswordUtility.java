package com.example.demo;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

public class PasswordUtility {

	
	public static void main(String[] args) {
		
		PasswordEncoder encoder = new BCryptPasswordEncoder();
		
		System.out.println("admin " + encoder.encode("admin"));
		System.out.println("user "  + encoder.encode("password"));
		
		
	}
}
