package com.example.api_rate_limiter.rate_limiter.service;

import com.example.api_rate_limiter.rate_limiter.entity.RateLimiter;

import java.util.List;

public interface RateLimiterService {
    RateLimiter findByUserName(String userName);
    void saveRateLimiter(RateLimiter rateLimiter);
    RateLimiter create(String userName);
    List<RateLimiter> findAll();
    void handleRequests(String userName, String callbackUrl);
}
