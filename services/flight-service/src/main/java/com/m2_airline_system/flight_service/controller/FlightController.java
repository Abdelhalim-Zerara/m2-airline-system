package com.m2_airline_system.flight_service.controller;

import com.m2_airline_system.flight_service.entity.Flight;
import com.m2_airline_system.flight_service.service.FlightService;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/flights")
public class FlightController {
    private final FlightService flightService;

    public FlightController(FlightService flightService) {
        this.flightService = flightService;
    }

    @GetMapping
    public List<Flight> searchFlights(@RequestParam String origin, @RequestParam String destination) {
        return flightService.findFlights(origin, destination);
    }

    @GetMapping("/{id}")
    public Flight getFlightById(@PathVariable Long id) {
        return flightService.findById(id);
    }
}