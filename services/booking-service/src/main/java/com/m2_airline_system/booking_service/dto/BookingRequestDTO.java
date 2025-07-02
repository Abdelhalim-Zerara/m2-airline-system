package com.m2_airline_system.booking_service.dto;

import lombok.Data;
@Data
public class BookingRequestDTO {
    private Long flightId;
    private Long customerId;
}
