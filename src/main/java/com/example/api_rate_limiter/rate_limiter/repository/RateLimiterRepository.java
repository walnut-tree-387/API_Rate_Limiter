package com.example.api_rate_limiter.rate_limiter.repository;

import com.example.api_rate_limiter.rate_limiter.entity.RateLimiter;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RateLimiterRepository extends JpaRepository<RateLimiter, Long> {
    Optional<RateLimiter> findByUsername(String username);
}
