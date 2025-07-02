package com.m2_airline_system.booking_service.service;

import com.m2_airline_system.booking_service.dto.BookingRequestDTO;
import com.m2_airline_system.booking_service.dto.BookingResponseDTO;
import com.m2_airline_system.booking_service.dto.CustomerDTO;
import com.m2_airline_system.booking_service.dto.NotificationRequest;
import com.m2_airline_system.booking_service.dto.TransactionRequest;
import com.m2_airline_system.booking_service.entity.Booking;
import com.m2_airline_system.booking_service.enums.BookingStatus;
import com.m2_airline_system.booking_service.feign.AccountingServiceClient;
import com.m2_airline_system.booking_service.feign.CustomerServiceClient;
import com.m2_airline_system.booking_service.feign.InventoryServiceClient;
import com.m2_airline_system.booking_service.feign.NotificationServiceClient;
import com.m2_airline_system.booking_service.repository.BookingRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;

@Service
@Slf4j
public class BookingService {
    private final BookingRepository bookingRepository;
    private final InventoryServiceClient inventoryServiceClient;
    private final CustomerServiceClient customerServiceClient;
    private final AccountingServiceClient accountingServiceClient;
    private final NotificationServiceClient notificationServiceClient;


    public BookingService(BookingRepository bookingRepository,
                          InventoryServiceClient inventoryServiceClient,
                          CustomerServiceClient customerServiceClient,
                          AccountingServiceClient accountingServiceClient,
                          NotificationServiceClient notificationServiceClient) {
        this.bookingRepository = bookingRepository;
        this.inventoryServiceClient = inventoryServiceClient;
        this.customerServiceClient = customerServiceClient;
        this.accountingServiceClient = accountingServiceClient;
        this.notificationServiceClient = notificationServiceClient;
    }

    @Transactional
    public BookingResponseDTO createBooking(BookingRequestDTO request) {
        // 1. Verify customer exists and get details
        CustomerDTO customer = customerServiceClient.getCustomerById(request.getCustomerId());

        // 2. Try to reserve a seat (-1)
        inventoryServiceClient.updateInventory(request.getFlightId(), -1)
                .getBody(); // Throws FeignException on non-2xx response

        // 3. Create and save booking
        Booking booking = new Booking();
        booking.setFlightId(request.getFlightId());
        booking.setCustomerId(request.getCustomerId());
        booking.setStatus(BookingStatus.CONFIRMED);
        Booking savedBooking = bookingRepository.save(booking);

        // 4. Trigger accounting and notification services asynchronously
        handlePostBookingTasks(savedBooking, customer);

        log.info("Booking {} created successfully for customer {}.", savedBooking.getId(), customer.getId());

        return BookingResponseDTO.builder()
                .bookingId(savedBooking.getId())
                .flightId(savedBooking.getFlightId())
                .customerId(savedBooking.getCustomerId())
                .status(savedBooking.getStatus().name())
                .build();
    }

    /**
     * Handles post-booking operations like accounting and notifications asynchronously.
     * @param booking The newly saved booking entity.
     * @param customer The customer details DTO.
     */
    @Async
    public void handlePostBookingTasks(Booking booking, CustomerDTO customer) {
        // Record the transaction in the accounting service
        try {
            TransactionRequest transactionRequest = TransactionRequest.builder()
                    .bookingId(booking.getId())
                    .amount(new BigDecimal("199.99")) // Dummy amount
                    .type("SALE")
                    .build();
            accountingServiceClient.recordTransaction(transactionRequest);
            log.info("Accounting record initiated for booking ID: {}", booking.getId());
        } catch (Exception e) {
            log.error("Failed to call accounting service for booking ID: {}", booking.getId(), e);
            // Here you could add logic to a retry queue
        }

        // Send a confirmation notification to the customer
        try {
            NotificationRequest notificationRequest = NotificationRequest.builder()
                    .type("EMAIL")
                    .to(customer.getEmail())
                    .message(String.format("Dear %s, your booking for flight %d is confirmed. Your booking ID is %d.",
                            customer.getName(), booking.getFlightId(), booking.getId()))
                    .build();
            notificationServiceClient.sendNotification(notificationRequest);
            log.info("Confirmation notification initiated for booking ID: {}", booking.getId());
        } catch (Exception e) {
            log.error("Failed to call notification service for booking ID: {}", booking.getId(), e);
            // Here you could add logic to a retry queue
        }
    }


    public Booking getBookingById(Long id) {
        return bookingRepository.findById(id).orElse(null);
    }
}