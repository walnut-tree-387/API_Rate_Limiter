package com.example.api_rate_limiter.rate_limiter.service;

import com.example.api_rate_limiter.rate_limiter.entity.RateLimiter;
import com.example.api_rate_limiter.rate_limiter.repository.RateLimiterRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class RateLimiterServiceImpl implements RateLimiterService {
    private final RateLimiterRepository rateLimiterRepository;

    public RateLimiterServiceImpl(RateLimiterRepository rateLimiterRepository) {
        this.rateLimiterRepository = rateLimiterRepository;
    }

    @Override
    public RateLimiter findByUserName(String userName) {
        Optional<RateLimiter> rateLimiter = rateLimiterRepository.findByUsername(userName);
        if(rateLimiter.isEmpty()) throw new RuntimeException("User not found");
        return rateLimiter.get();
    }
}
