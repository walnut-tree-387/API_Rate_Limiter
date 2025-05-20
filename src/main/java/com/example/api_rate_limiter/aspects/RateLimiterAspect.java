package com.example.api_rate_limiter.aspects;

import com.example.api_rate_limiter.exceptions.types.TooManyRequestsException;
import com.example.api_rate_limiter.rate_limiter.entity.RateLimiter;
import com.example.api_rate_limiter.rate_limiter.service.RateLimiterService;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

@Component
@Aspect
public class RateLimiterAspect {
    private static final Long MAX_TRY = 5L;
    private final RateLimiterService rateLimiterService;
    public RateLimiterAspect(RateLimiterService rateLimiterService) {
        this.rateLimiterService = rateLimiterService;
    }
    @Around("@annotation(com.example.api_rate_limiter.aspects.RateLimited)")
    public Object limiter(ProceedingJoinPoint joinPoint) throws Throwable {
        String userName = getUserName();
        RateLimiter limiter = rateLimiterService.findByUserName(userName);
        if(limiter.getHitCount() >= MAX_TRY) {
            throw new TooManyRequestsException(RateLimiter.class, "Rate limit exceeded");
        }
        limiter.setHitCount(limiter.getHitCount() + 1);
        rateLimiterService.saveRateLimiter(limiter);
        return joinPoint.proceed();
    }
    private String getUserName() {
        SecurityContext securityContext = SecurityContextHolder.getContext();
        Object principal = securityContext.getAuthentication().getPrincipal();
        return principal != null ? principal.toString() : "";
    }
}
