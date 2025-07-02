package com.m2_airline_system.search_service.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class FlightSearchResultDTO {
    private Long flightId;
    private String flightNumber;
    private String origin;
    private String destination;
    private int availableSeats;
}