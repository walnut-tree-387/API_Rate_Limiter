package com.example.api_rate_limiter;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableScheduling
@SpringBootApplication
public class ApiRateLimiterApplication {

	public static void main(String[] args) {
		SpringApplication.run(ApiRateLimiterApplication.class, args);
	}

}
