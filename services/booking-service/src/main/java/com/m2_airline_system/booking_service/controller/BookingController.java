package com.m2_airline_system.booking_service.controller;

import com.m2_airline_system.booking_service.dto.BookingRequestDTO;
import com.m2_airline_system.booking_service.dto.BookingResponseDTO;
import com.m2_airline_system.booking_service.entity.Booking;
import com.m2_airline_system.booking_service.service.BookingService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/bookings")
public class BookingController {
    private final BookingService bookingService;

    public BookingController(BookingService bookingService) {
        this.bookingService = bookingService;
    }

    @PostMapping
    public BookingResponseDTO createBooking(@RequestBody BookingRequestDTO request) {
        return bookingService.createBooking(request);
    }

    @GetMapping("/{id}")
    public Booking getBookingById(@PathVariable Long id) {
        return bookingService.getBookingById(id);
    }
}