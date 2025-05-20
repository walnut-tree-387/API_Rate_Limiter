package com.example.api_rate_limiter.scheduler;

import com.example.api_rate_limiter.rate_limiter.entity.RateLimiter;
import com.example.api_rate_limiter.rate_limiter.service.RateLimiterService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.List;
@Slf4j
@Component
public class LocalScheduler {

    private final RateLimiterService limiterService;

    public LocalScheduler(RateLimiterService limiterService) {
        this.limiterService = limiterService;
    }

    @Scheduled(fixedRate = 1000)
    public void schedule() {
        List<RateLimiter> allEntries = limiterService.findAll();
        allEntries.forEach(limiter -> {
            if(limiter.getStartTime() + 60000L < System.currentTimeMillis()) {
                limiter.setStartTime(System.currentTimeMillis());
                limiter.setHitCount(0L);
                limiterService.saveRateLimiter(limiter);
                log.info("Updated Rate Limiter for {}", limiter.getUsername());
            }
        });
    }
}
