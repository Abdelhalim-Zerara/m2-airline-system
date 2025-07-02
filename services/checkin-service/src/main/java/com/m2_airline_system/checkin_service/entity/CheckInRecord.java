package com.m2_airline_system.checkin_service.entity;

import jakarta.persistence.*;
import lombok.Data;
import java.time.LocalDateTime;

@Entity
@Data
public class CheckInRecord {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Long bookingId;
    private String seatNumber;
    private LocalDateTime checkInTime;
}
