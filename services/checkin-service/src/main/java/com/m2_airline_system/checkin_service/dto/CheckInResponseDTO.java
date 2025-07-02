package com.m2_airline_system.checkin_service.dto;

import lombok.Builder;
import lombok.Data;
@Data
@Builder
public class CheckInResponseDTO {
    private Long checkInId;
    private Long bookingId;
    private String seatNumber;
    private String message;
}