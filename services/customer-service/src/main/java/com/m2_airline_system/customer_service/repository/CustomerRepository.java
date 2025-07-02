package com.m2_airline_system.customer_service.repository;

import com.m2_airline_system.customer_service.entity.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
public interface CustomerRepository extends JpaRepository<Customer, Long> {}
