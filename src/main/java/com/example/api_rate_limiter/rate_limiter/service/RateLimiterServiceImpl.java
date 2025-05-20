package com.example.api_rate_limiter.rate_limiter.service;

import com.example.api_rate_limiter.rate_limiter.entity.RateLimiter;
import com.example.api_rate_limiter.rate_limiter.repository.RateLimiterRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.Optional;
@Slf4j
@Service
public class RateLimiterServiceImpl implements RateLimiterService {
    private final RateLimiterRepository rateLimiterRepository;

    public RateLimiterServiceImpl(RateLimiterRepository rateLimiterRepository) {
        this.rateLimiterRepository = rateLimiterRepository;
    }

    @Override
    public RateLimiter findByUserName(String userName) {
        Optional<RateLimiter> rateLimiter = rateLimiterRepository.findByUsername(userName);
        if(rateLimiter.isEmpty()) {
            log.info("No user found with username {}", userName);
            return create(userName);
        }
        return rateLimiter.get();
    }

    @Override
    public void saveRateLimiter(RateLimiter rateLimiter) {
        rateLimiterRepository.save(rateLimiter);
    }

    @Override
    public RateLimiter create(String userName) {
        RateLimiter limiter = new RateLimiter();
        log.info("Creating new entry for this username {}", userName);
        limiter.setUsername(userName);
        limiter.setStartTime(Instant.now().getEpochSecond());
        return rateLimiterRepository.save(limiter);
    }
}
