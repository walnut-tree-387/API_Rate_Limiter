package com.example.api_rate_limiter.request_queue;

import com.example.api_rate_limiter.request_queue.entity.RequestQueue;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RequestQueueRepository extends JpaRepository<RequestQueue, Long> {
    @Query("    SELECT rq FROM RequestQueue rq WHERE  rq.queueStatus = 'PENDING' " +
            "   ORDER BY rq.createdAt ASC ")
    List<RequestQueue> getCurrentRequestQueue();
    @Query("    SELECT COUNT(rq) FROM RequestQueue rq WHERE  rq.queueStatus = 'PENDING' ")
    Long getNextRequestQueueSerialNumber();
}
