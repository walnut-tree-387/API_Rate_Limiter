package com.example.api_rate_limiter.request_queue.service;

import com.example.api_rate_limiter.request_queue.entity.RequestQueue;

import java.util.List;

public interface RequestQueueService {
    Long addNewRequest(String callbackUrl, String userName);
    Long executeBurstProcessing(String userName);
    void processTopRequest();
}
