package com.m2_airline_system.booking_service.feign;

import com.m2_airline_system.booking_service.dto.TransactionRequest;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "accounting-service")
public interface AccountingServiceClient {

    @PostMapping("/accounting/transaction")
    void recordTransaction(@RequestBody TransactionRequest request);
}