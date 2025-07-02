package com.m2_airline_system.notification_service;

import lombok.Data;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/notifications")
public class NotificationController {
    private static final Logger log = LoggerFactory.getLogger(NotificationController.class);

    @PostMapping
    public void sendNotification(@RequestBody NotificationRequest request) {
        log.info("Simulating sending notification: Type={}, To={}, Message='{}'",
                request.getType(), request.getTo(), request.getMessage());
    }
}

// NotificationRequest.java
@Data
class NotificationRequest {
    private String type; // SMS, EMAIL
    private String to;
    private String message;
}