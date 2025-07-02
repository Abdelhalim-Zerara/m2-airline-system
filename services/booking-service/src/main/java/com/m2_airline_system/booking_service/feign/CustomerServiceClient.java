package com.m2_airline_system.booking_service.feign;

import com.m2_airline_system.booking_service.dto.CustomerDTO;
import lombok.Data;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "customer-service")
public interface CustomerServiceClient {
    @GetMapping("/customers/{id}")
    CustomerDTO getCustomerById(@PathVariable Long id);
}