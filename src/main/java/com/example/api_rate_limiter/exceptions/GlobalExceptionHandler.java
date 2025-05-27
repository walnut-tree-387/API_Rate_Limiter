package com.example.api_rate_limiter.exceptions;

import com.example.api_rate_limiter.exceptions.types.TooManyRequestsException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import java.util.HashMap;
import java.util.Map;
import java.util.TooManyListenersException;

@Slf4j
@ControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler({TooManyRequestsException.class})
    public ResponseEntity<Object> handleMethodArgumentException(TooManyRequestsException e) {
        Map<String,Object> response = new HashMap<>();
        response.put("code", HttpStatus.TOO_MANY_REQUESTS.value());
        response.put("Error message", e.getMessage());
        response.put("Queue Position", e.getQueuePosition());
        response.put("Estimated wait time", e.getEstimatedWaitTime());
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }
}
