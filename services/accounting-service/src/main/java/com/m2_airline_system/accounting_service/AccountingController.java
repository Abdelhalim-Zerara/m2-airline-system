package com.m2_airline_system.accounting_service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import lombok.Data;
import java.math.BigDecimal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/accounting")
public class AccountingController {
    private static final Logger log = LoggerFactory.getLogger(AccountingController.class);

    @PostMapping("/transaction")
    public void recordTransaction(@RequestBody TransactionRequest request) {
        log.info("Recording transaction: BookingId={}, Amount={}, Type={}",
                request.getBookingId(), request.getAmount(), request.getType());
    }
}


@Data
class TransactionRequest {
    private Long bookingId;
    private BigDecimal amount;
    private String type; // SALE, REFUND
}