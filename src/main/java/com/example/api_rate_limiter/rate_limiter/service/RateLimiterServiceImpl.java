package com.example.api_rate_limiter.rate_limiter.service;

import com.example.api_rate_limiter.exceptions.types.TooManyRequestsException;
import com.example.api_rate_limiter.rate_limiter.entity.RateLimiter;
import com.example.api_rate_limiter.rate_limiter.repository.RateLimiterRepository;
import com.example.api_rate_limiter.request_queue.entity.RequestQueue;
import com.example.api_rate_limiter.request_queue.service.RequestQueueService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.List;
import java.util.Optional;
@Slf4j
@Service
public class RateLimiterServiceImpl implements RateLimiterService {
    private final RateLimiterRepository rateLimiterRepository;
    private static final Long MAX_TRY = 4L;
    private final RequestQueueService requestQueueService;

    public RateLimiterServiceImpl(RateLimiterRepository rateLimiterRepository, RequestQueueService requestQueueService) {
        this.rateLimiterRepository = rateLimiterRepository;
        this.requestQueueService = requestQueueService;
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

    @Override
    public List<RateLimiter> findAll() {
        return rateLimiterRepository.findAll();
    }

    @Override
    public void handleRequests(String userName, String callbackUrl) {
        RateLimiter limiter = findByUserName(userName);
        if(limiter.getHitCount() >= MAX_TRY) {
            Long userSerial = requestQueueService.addNewRequest(callbackUrl, userName);
            throw new TooManyRequestsException(RateLimiter.class, "Rate limit exceeded",
                    userSerial, userSerial * 10L);
        }
        limiter.setHitCount(limiter.getHitCount() + 1);
        saveRateLimiter(limiter);
    }
}
