package com.m2_airline_system.checkin_service.service;

import com.m2_airline_system.checkin_service.dto.CheckInRequestDTO;
import com.m2_airline_system.checkin_service.dto.CheckInResponseDTO;
import com.m2_airline_system.checkin_service.entity.CheckInRecord;
import com.m2_airline_system.checkin_service.feign.BookingServiceClient;
import com.m2_airline_system.checkin_service.repository.CheckInRecordRepository;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.util.Random;

@Service
public class CheckInService {
    private final CheckInRecordRepository checkInRepository;
    private final BookingServiceClient bookingServiceClient;


    public CheckInService(CheckInRecordRepository repository, BookingServiceClient client) {
        this.checkInRepository = repository;
        this.bookingServiceClient = client;
    }

    public CheckInResponseDTO performCheckIn(CheckInRequestDTO request) {
        // 1. Get booking details
        BookingServiceClient.BookingDTO booking = bookingServiceClient.getBookingById(request.getBookingId());
        if (booking == null || !"CONFIRMED".equals(booking.getStatus())) {
            throw new IllegalStateException("Booking not found or not in a check-inable state.");
        }

        // 2. Assign a seat (simple logic)
        String seat = (new Random().nextInt(30) + 1) + "" + (char) ('A' + new Random().nextInt(6));

        // 3. Create and save check-in record
        CheckInRecord record = new CheckInRecord();
        record.setBookingId(request.getBookingId());
        record.setSeatNumber(seat);
        record.setCheckInTime(LocalDateTime.now());
        CheckInRecord savedRecord = checkInRepository.save(record);

        // Optionally update booking status to CHECKED_IN via Feign

        return CheckInResponseDTO.builder()
                .checkInId(savedRecord.getId())
                .bookingId(savedRecord.getBookingId())
                .seatNumber(savedRecord.getSeatNumber())
                .message("Check-in successful. Boarding pass generated.")
                .build();
    }
}