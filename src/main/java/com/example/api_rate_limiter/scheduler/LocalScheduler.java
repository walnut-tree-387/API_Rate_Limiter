package com.example.api_rate_limiter.scheduler;

import com.example.api_rate_limiter.rate_limiter.entity.RateLimiter;
import com.example.api_rate_limiter.rate_limiter.service.RateLimiterService;
import com.example.api_rate_limiter.request_queue.entity.RequestQueue;
import com.example.api_rate_limiter.request_queue.service.RequestQueueService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
@Slf4j
@Component
public class LocalScheduler {

    private final RateLimiterService limiterService;
    private final RequestQueueService requestQueueService;

    public LocalScheduler(RateLimiterService limiterService, RequestQueueService requestQueueService) {
        this.limiterService = limiterService;
        this.requestQueueService = requestQueueService;
    }
    @Scheduled(fixedRate = 30000)
    public void processQueuedRequest(){
        requestQueueService.processTopRequest();
        log.info("Current time : {}", getFormattedLocalTime());
    }

    @Scheduled(fixedRate = 1000)
    public void schedule() {
        List<RateLimiter> allEntries = limiterService.findAll();
        allEntries.forEach(limiter -> {
            if(limiter.getStartTime() + 300000 < System.currentTimeMillis()) {  // Waiting window is over for this user
                Long requestExecuted = requestQueueService.executeBurstProcessing(limiter.getUsername());
                limiter.setStartTime(System.currentTimeMillis());
                limiter.setHitCount(requestExecuted);
                limiterService.saveRateLimiter(limiter);
                log.info("Updated Rate Limiter for {} {}", limiter.getUsername(), getFormattedLocalTime());
                log.info("Current hit count for  {} : {}", limiter.getUsername(), limiter.getHitCount());
            }
        });
    }
    public String getFormattedLocalTime(){
        Instant now = Instant.now();
        ZoneId zoneId = ZoneId.of("Asia/Dhaka");
        ZonedDateTime timeInUTCPlus630 = now.atZone(zoneId);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("hh:mm:ss a");
        return timeInUTCPlus630.format(formatter);
    }
}
