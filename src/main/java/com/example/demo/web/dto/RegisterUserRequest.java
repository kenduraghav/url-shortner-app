package com.example.demo.web.dto;

import jakarta.validation.constraints.NotBlank;

public record RegisterUserRequest(
		
		@NotBlank(message = "Email is required.") 
//		@Email(message = "Please enter a valid email id.", 
//		regexp = "[a-z0-9._%+-]+@[a-z0-9.-]+\\.[a-z]{2,3}", 
//		flags = Pattern.Flag.CASE_INSENSITIVE) 
		String email,
		@NotBlank(message = "Password cannot be blank") 
//		@Pattern(regexp = "^(?=.*[0-9])(?=.*[a-zA-Z])(?=.*[@#$%^&+=!]).{8,}$", 
//		message = "Password must be at least 8 characters long, include a number, a letter, and a special character") 
		String password,

		@NotBlank(message = "Name cannot be blank") 
		String name) {

}
