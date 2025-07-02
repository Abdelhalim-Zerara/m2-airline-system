package com.m2_airline_system.flight_service.Repository;

import com.m2_airline_system.flight_service.entity.Flight;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface FlightRepository extends JpaRepository<Flight, Long> {
    List<Flight> findByOriginAndDestination(String origin, String destination);
}