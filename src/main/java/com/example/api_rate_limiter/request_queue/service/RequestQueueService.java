package com.example.api_rate_limiter.request_queue.service;

import com.example.api_rate_limiter.request_queue.entity.RequestQueue;

public interface RequestQueueService {
    RequestQueue addNewRequest(String callbackUrl, String userName);
}
