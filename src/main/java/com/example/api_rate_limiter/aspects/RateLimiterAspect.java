package com.example.api_rate_limiter.aspects;

import com.example.api_rate_limiter.exceptions.types.TooManyRequestsException;
import com.example.api_rate_limiter.rate_limiter.entity.RateLimiter;
import com.example.api_rate_limiter.rate_limiter.service.RateLimiterService;
import jakarta.servlet.http.HttpServletRequest;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

@Component
@Aspect
public class RateLimiterAspect {
    private final RateLimiterService rateLimiterService;
    public RateLimiterAspect(RateLimiterService rateLimiterService) {
        this.rateLimiterService = rateLimiterService;
    }
    @Around("@annotation(com.example.api_rate_limiter.aspects.RateLimited)")
    public Object limiter(ProceedingJoinPoint joinPoint) throws Throwable {
        String userName = getUserName();
        rateLimiterService.handleRequests(userName, extractCallbackUrl());
        return joinPoint.proceed();
    }
    private String getUserName() {
        SecurityContext securityContext = SecurityContextHolder.getContext();
        Object principal = securityContext.getAuthentication().getPrincipal();
        return principal != null ? principal.toString() : "";
    }
    public String extractCallbackUrl(){
        RequestAttributes requestAttributes = RequestContextHolder.getRequestAttributes();
        if(requestAttributes != null){
            return ((ServletRequestAttributes) requestAttributes).getRequest().getParameter("callbackUrl");
        }
        else return "no callback url found";
    }
}
