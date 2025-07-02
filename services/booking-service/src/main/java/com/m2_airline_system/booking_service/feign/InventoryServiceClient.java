package com.m2_airline_system.booking_service.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "inventory-service")
public interface InventoryServiceClient {
    @PostMapping("/inventory/update")
    ResponseEntity<Void> updateInventory(@RequestParam Long flightId, @RequestParam int quantity);
}
