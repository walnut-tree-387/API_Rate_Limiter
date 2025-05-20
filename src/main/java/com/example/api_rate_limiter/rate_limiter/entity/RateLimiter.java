package com.example.api_rate_limiter.rate_limiter.entity;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "rate_limiter_entries")
public class RateLimiter {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long id;
    private String username;
    private Long hitCount = 0L;
    private Long startTime;
}
