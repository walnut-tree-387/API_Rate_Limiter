package com.example.api_rate_limiter.request_queue.entity;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "request_queue")
public class RequestQueue {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long id;
    private String userName;
    private Long createdAt;
    private Long processedAt;

    @Enumerated(EnumType.STRING)
    private QueueStatus queueStatus;
    private String callbackUrl;
}
