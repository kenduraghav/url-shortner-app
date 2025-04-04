package com.example.demo.domain.services;

import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URL;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class URLChecker {
	
	private static final Logger log = LoggerFactory.getLogger(URLChecker.class);

	public static boolean isURLExists(String originalUrl) {
		log.debug("Validating URL Exists or not " + originalUrl);
		try {
			URL url = new URI(originalUrl).toURL();
			HttpURLConnection connection = (HttpURLConnection) url.openConnection();
			connection.setRequestMethod("HEAD"); // Use HEAD for fast response
			connection.setConnectTimeout(5000); // Timeout in milliseconds
			connection.setReadTimeout(5000);
			int responseCode = connection.getResponseCode();
			log.debug("URL Response Code " + responseCode);
			return (responseCode >= 200 && responseCode < 400); // 2xx and 3xx codes are valid
		} catch (Exception e) {
			return false; // Invalid URL or server not reachable
		}

	}
}
