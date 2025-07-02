package com.m2_airline_system.inventory_service.component;

import com.m2_airline_system.inventory_service.entity.Inventory;
import com.m2_airline_system.inventory_service.repository.InventoryRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class DataLoader implements CommandLineRunner {
    private final InventoryRepository inventoryRepository;

    public DataLoader(InventoryRepository inventoryRepository) {
        this.inventoryRepository = inventoryRepository;
    }

    @Override
    public void run(String... args) throws Exception {
        // Supposons que les IDs de vol sont 1 et 2
        inventoryRepository.save(new Inventory(1L, 150));
        inventoryRepository.save(new Inventory(2L, 100));
    }
}