package com.example.api_rate_limiter.rate_limiter;

import org.apache.coyote.Response;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1")
public class DummyController {

    @GetMapping("/dummy")
    public ResponseEntity<String> dummy() {
        return ResponseEntity.ok("Hello buddy");
    }
}
