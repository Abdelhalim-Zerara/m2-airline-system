package com.m2_airline_system.checkin_service.feign;

import lombok.Data;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "booking-service")
public interface BookingServiceClient {
    @GetMapping("/bookings/{id}")
    BookingDTO getBookingById(@PathVariable Long id);

    @Data
    class BookingDTO {
        private Long id;
        private String status;
    }
}