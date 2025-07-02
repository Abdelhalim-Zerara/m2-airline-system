package com.m2_airline_system.customer_service.service;

import com.m2_airline_system.customer_service.entity.Customer;
import com.m2_airline_system.customer_service.repository.CustomerRepository;
import org.springframework.stereotype.Service;
@Service
public class CustomerService {
    private final CustomerRepository customerRepository;

    public CustomerService(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    public Customer createCustomer(Customer customer) {
        return customerRepository.save(customer);
    }

    public Customer getCustomerById(Long id) {
        return customerRepository.findById(id).orElse(null);
    }
}
