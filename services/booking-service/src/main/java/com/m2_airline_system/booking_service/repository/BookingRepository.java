package com.m2_airline_system.booking_service.repository;

import com.m2_airline_system.booking_service.entity.Booking;
import org.springframework.data.jpa.repository.JpaRepository;
public interface BookingRepository extends JpaRepository<Booking, Long> {}