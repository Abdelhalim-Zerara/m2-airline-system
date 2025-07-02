package com.m2_airline_system.booking_service.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class NotificationRequest {
    private String type; // SMS, EMAIL
    private String to;
    private String message;
}