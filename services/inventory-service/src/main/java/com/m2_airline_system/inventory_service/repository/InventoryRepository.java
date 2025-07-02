package com.m2_airline_system.inventory_service.repository;

import com.m2_airline_system.inventory_service.entity.Inventory;
import org.springframework.data.jpa.repository.JpaRepository;

public interface InventoryRepository extends JpaRepository<Inventory, Long> {}
