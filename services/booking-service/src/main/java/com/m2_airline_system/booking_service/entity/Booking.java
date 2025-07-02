package com.m2_airline_system.booking_service.entity;
import com.m2_airline_system.booking_service.enums.BookingStatus;
import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
public class Booking {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Long flightId;
    private Long customerId;
    @Enumerated(EnumType.STRING)
    private BookingStatus status;
}

