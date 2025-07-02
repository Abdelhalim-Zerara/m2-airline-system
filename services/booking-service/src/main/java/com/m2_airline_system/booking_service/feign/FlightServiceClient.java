package com.m2_airline_system.booking_service.feign;

import lombok.Data;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "flight-service")
public interface FlightServiceClient {
    @GetMapping("/flights/{id}")
    FlightDTO getFlightById(@PathVariable Long id);

    @Data
    class FlightDTO {
        private String flightNumber;
    }
}

