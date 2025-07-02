package com.m2_airline_system.flight_service.component;

import com.m2_airline_system.flight_service.Repository.FlightRepository;
import com.m2_airline_system.flight_service.entity.Flight;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class DataLoader implements CommandLineRunner {
    private final FlightRepository flightRepository;

    public DataLoader(FlightRepository flightRepository) {
        this.flightRepository = flightRepository;
    }

    @Override
    public void run(String... args) throws Exception {
        flightRepository.save(new Flight("M2-101", "Paris", "New York", "10:00", "12:00"));
        flightRepository.save(new Flight("M2-102", "Paris", "Tokyo", "22:00", "17:00"));
    }
}