package com.m2_airline_system.search_service.feign;

import lombok.Data;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import java.util.List;

@FeignClient(name = "flight-service")
public interface FlightServiceClient {
    @GetMapping("/flights")
    List<FlightDTO> searchFlights(@RequestParam("origin") String origin, @RequestParam("destination") String destination);

    @Data
    class FlightDTO {
        private Long id;
        private String flightNumber;
        private String origin;
        private String destination;
    }
}