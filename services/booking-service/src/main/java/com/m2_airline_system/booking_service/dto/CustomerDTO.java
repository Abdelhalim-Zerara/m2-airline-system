package com.m2_airline_system.booking_service.dto;

import lombok.Data;

@Data
public class CustomerDTO {
    private Long id;
    private String name;
    private String email;
}