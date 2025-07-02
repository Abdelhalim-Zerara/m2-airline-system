package com.m2_airline_system.inventory_service.service;

import com.m2_airline_system.inventory_service.entity.Inventory;
import com.m2_airline_system.inventory_service.repository.InventoryRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class InventoryService {
    private final InventoryRepository inventoryRepository;

    public InventoryService(InventoryRepository inventoryRepository) {
        this.inventoryRepository = inventoryRepository;
    }

    public Inventory getInventoryByFlightId(Long flightId) {
        return inventoryRepository.findById(flightId).orElse(new Inventory(flightId, 0));
    }

    @Transactional
    public boolean updateInventory(Long flightId, int quantity) {
        Inventory inventory = inventoryRepository.findById(flightId).orElse(null);
        if (inventory != null && inventory.getAvailableSeats() + quantity >= 0) {
            inventory.setAvailableSeats(inventory.getAvailableSeats() + quantity);
            inventoryRepository.save(inventory);
            return true;
        }
        return false;
    }
}