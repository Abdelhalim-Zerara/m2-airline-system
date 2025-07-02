package com.m2_airline_system.flight_service.dto;

import lombok.Data;

@Data
public class FlightDTO {
    private String flightNumber;
    private String origin;
    private String destination;
}
