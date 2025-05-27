package com.example.api_rate_limiter.exceptions.types;

import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
public class TooManyRequestsException extends RuntimeException {
    private Long queuePosition;
    private Long estimatedWaitTime;
    public TooManyRequestsException (Class<?> clazz, String message, Long queuePosition, Long estimatedWaitTime) {
        super(String.format(message, clazz.getSimpleName()));
        this.queuePosition = queuePosition;
        this.estimatedWaitTime = estimatedWaitTime;
    }
}
