package com.example.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;

@SpringBootApplication
@ConfigurationPropertiesScan
public class UrlShortnerAppApplication {

	public static void main(String[] args) {
		SpringApplication.run(UrlShortnerAppApplication.class, args);
	}

}
