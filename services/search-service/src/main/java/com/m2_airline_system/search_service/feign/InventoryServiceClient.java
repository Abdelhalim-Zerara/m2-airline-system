package com.m2_airline_system.search_service.feign;

import lombok.Data;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "inventory-service")
public interface InventoryServiceClient {
    @GetMapping("/inventory/{flightId}")
    InventoryDTO getInventory(@PathVariable("flightId") Long flightId);

    @Data
    class InventoryDTO {
        private int availableSeats;
    }
}