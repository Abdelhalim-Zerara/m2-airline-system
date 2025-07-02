package com.m2_airline_system.checkin_service.controller;



import com.m2_airline_system.checkin_service.dto.CheckInRequestDTO;
import com.m2_airline_system.checkin_service.dto.CheckInResponseDTO;
import com.m2_airline_system.checkin_service.service.CheckInService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/checkin")
public class CheckInController {

    private final CheckInService checkInService;

    public CheckInController(CheckInService checkInService) {
        this.checkInService = checkInService;
    }

    @PostMapping
    public ResponseEntity<CheckInResponseDTO> checkIn(@RequestBody CheckInRequestDTO request) {
        try {
            CheckInResponseDTO response = checkInService.performCheckIn(request);
            return ResponseEntity.ok(response);
        } catch (IllegalStateException e) {
            return ResponseEntity.badRequest().body(
                CheckInResponseDTO.builder()
                    .bookingId(request.getBookingId())
                    .message("Check-in failed: " + e.getMessage())
                    .build()
            );
        }
    }
}