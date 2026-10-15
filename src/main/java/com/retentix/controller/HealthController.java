
package com.retentix.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HealthController {

    //http://localhost:8081/api/health
    @GetMapping("/api/health")
    public String healthCheck() {
        return "RETENTIX API is running successfully!";
    }
}
