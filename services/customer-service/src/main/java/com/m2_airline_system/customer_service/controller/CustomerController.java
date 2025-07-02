package com.m2_airline_system.customer_service.controller;

import com.m2_airline_system.customer_service.entity.Customer;
import com.m2_airline_system.customer_service.service.CustomerService;
import org.springframework.web.bind.annotation.*;
@RestController
@RequestMapping("/customers")
public class CustomerController {
    private final CustomerService customerService;

    public CustomerController(CustomerService customerService) {
        this.customerService = customerService;
    }

    @PostMapping
    public Customer createCustomer(@RequestBody Customer customer) {
        return customerService.createCustomer(customer);
    }

    @GetMapping("/{id}")
    public Customer getCustomerById(@PathVariable Long id) {
        return customerService.getCustomerById(id);
    }
}