package com.m2_airline_system.booking_service.dto;

import lombok.Builder;
import lombok.Data;
import java.math.BigDecimal;

@Data
@Builder
public class TransactionRequest {
    private Long bookingId;
    private BigDecimal amount;
    private String type; // SALE, REFUND
}