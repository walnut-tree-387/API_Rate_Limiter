package com.example.api_rate_limiter.request_queue.service;

import com.example.api_rate_limiter.request_queue.RequestQueueRepository;
import com.example.api_rate_limiter.request_queue.entity.QueueStatus;
import com.example.api_rate_limiter.request_queue.entity.RequestQueue;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RequestQueueServiceImpl implements RequestQueueService {
    private final RequestQueueRepository requestQueueRepository;

    public RequestQueueServiceImpl(RequestQueueRepository requestQueueRepository) {
        this.requestQueueRepository = requestQueueRepository;
    }

    @Override
    public RequestQueue addNewRequest(String callbackUrl, String userName) {
        RequestQueue requestQueue = new RequestQueue();
        requestQueue.setQueueStatus(QueueStatus.PENDING);
        requestQueue.setUserName(userName);
        requestQueue.setCallbackUrl(callbackUrl);
        requestQueue.setCreatedAt(System.currentTimeMillis());
        requestQueue.setSerialNumber(getNextRequestQueueSerialNumber() + 1L);
        return requestQueueRepository.save(requestQueue);
    }
    public List<RequestQueue> getCurrentRequestQueue() {
        return requestQueueRepository.getCurrentRequestQueue();
    }
    public Long getNextRequestQueueSerialNumber(){
        return requestQueueRepository.getNextRequestQueueSerialNumber();
    }
}
