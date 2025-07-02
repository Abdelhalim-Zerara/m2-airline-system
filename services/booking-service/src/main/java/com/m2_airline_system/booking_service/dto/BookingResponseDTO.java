package com.m2_airline_system.booking_service.dto;

import lombok.Builder;
import lombok.Data;
@Data
@Builder
public class BookingResponseDTO {
    private Long bookingId;
    private Long flightId;
    private Long customerId;
    private String status;
}