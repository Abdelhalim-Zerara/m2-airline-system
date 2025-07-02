package com.m2_airline_system.booking_service.feign;

import com.m2_airline_system.booking_service.dto.NotificationRequest;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

// "notification-service" is the name registered with the discovery server
@FeignClient(name = "notification-service")
public interface NotificationServiceClient {

    @PostMapping("/notifications")
    void sendNotification(@RequestBody NotificationRequest request);
}