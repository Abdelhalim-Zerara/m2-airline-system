package com.m2_airline_system.flight_service.service;

import com.m2_airline_system.flight_service.Repository.FlightRepository;
import com.m2_airline_system.flight_service.entity.Flight;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class FlightService {
    private final FlightRepository flightRepository;

    public FlightService(FlightRepository flightRepository) {
        this.flightRepository = flightRepository;
    }

    public List<Flight> findFlights(String origin, String destination) {
        return flightRepository.findByOriginAndDestination(origin, destination);
    }

    public Flight findById(Long id) {
        return flightRepository.findById(id).orElse(null);
    }
}