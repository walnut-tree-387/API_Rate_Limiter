package com.example.api_rate_limiter.request_queue.service;

import com.example.api_rate_limiter.request_queue.RequestQueueRepository;
import com.example.api_rate_limiter.request_queue.entity.QueueStatus;
import com.example.api_rate_limiter.request_queue.entity.RequestQueue;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

import static java.lang.Math.max;
@Slf4j
@Service
public class RequestQueueServiceImpl implements RequestQueueService {
    private final RequestQueueRepository requestQueueRepository;

    public RequestQueueServiceImpl(RequestQueueRepository requestQueueRepository) {
        this.requestQueueRepository = requestQueueRepository;
    }

    @Override
    public Long addNewRequest(String callbackUrl, String userName) {
        RequestQueue requestQueue = new RequestQueue();
        requestQueue.setQueueStatus(QueueStatus.PENDING);
        requestQueue.setUserName(userName);
        requestQueue.setCallbackUrl(callbackUrl);
        requestQueue.setCreatedAt(System.currentTimeMillis());
        requestQueueRepository.save(requestQueue);
        return getNextRequestQueueSerialNumber();
    }

    @Override
    public Long executeBurstProcessing(String userName) {
        List<RequestQueue> queuedRequest = checkIfUserHasQueuedRequest(userName);
        long limit = Math.min(queuedRequest.size(), 5L);
        for(long i = 0L; i < limit ; i++){
            RequestQueue request = queuedRequest.get((int) i);
            request.setQueueStatus(QueueStatus.SUCCEEDED);
            requestQueueRepository.save(request);
        }
        return limit;
    }

    @Override
    public void processTopRequest() {
        List<RequestQueue> queuedRequest = getCurrentRequestQueue();
        if(!queuedRequest.isEmpty()){
            RequestQueue tobeProcessed = queuedRequest.get(0);
            tobeProcessed.setQueueStatus(QueueStatus.SUCCEEDED);
            requestQueueRepository.save(tobeProcessed);
            log.info("Processed queued request for user {}", tobeProcessed.getUserName());
        }
    }

    public List<RequestQueue> checkIfUserHasQueuedRequest(String userName) {
        return requestQueueRepository.checkIfUserHasQueuedRequest(userName);
    }

    public List<RequestQueue> getCurrentRequestQueue() {
        return requestQueueRepository.getCurrentRequestQueue();
    }
    public Long getNextRequestQueueSerialNumber(){
        return requestQueueRepository.getNextRequestQueueSerialNumber();
    }
}
