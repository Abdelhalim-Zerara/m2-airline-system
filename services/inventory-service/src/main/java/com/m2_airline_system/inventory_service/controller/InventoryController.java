package com.m2_airline_system.inventory_service.controller;

import com.m2_airline_system.inventory_service.entity.Inventory;
import com.m2_airline_system.inventory_service.service.InventoryService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/inventory")
public class InventoryController {
    private final InventoryService inventoryService;

    public InventoryController(InventoryService inventoryService) {
        this.inventoryService = inventoryService;
    }

    @GetMapping("/{flightId}")
    public Inventory getInventory(@PathVariable Long flightId) {
        return inventoryService.getInventoryByFlightId(flightId);
    }

    @PostMapping("/update")
    public ResponseEntity<Void> updateInventory(@RequestParam Long flightId, @RequestParam int quantity) {
        boolean success = inventoryService.updateInventory(flightId, quantity);
        return success ? ResponseEntity.ok().build() : ResponseEntity.badRequest().build();
    }
}