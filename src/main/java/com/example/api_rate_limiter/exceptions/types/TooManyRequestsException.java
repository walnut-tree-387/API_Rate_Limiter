package com.example.api_rate_limiter.exceptions.types;

public class TooManyRequestsException extends RuntimeException {
    public TooManyRequestsException (Class<?> clazz, String message) {
        super(String.format(message, clazz.getSimpleName()));
    }
}
