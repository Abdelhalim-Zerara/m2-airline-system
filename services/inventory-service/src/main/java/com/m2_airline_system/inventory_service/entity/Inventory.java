package com.m2_airline_system.inventory_service.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;

import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
public class Inventory {
    @Id
    private Long flightId; // Using flightId as primary key
    private int availableSeats;

    public Inventory(Long flightId, int availableSeats) {
        this.flightId = flightId;
        this.availableSeats = availableSeats;
    }
}

