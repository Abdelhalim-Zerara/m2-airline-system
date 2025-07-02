package com.m2_airline_system.customer_service;

import com.m2_airline_system.customer_service.entity.Customer;
import com.m2_airline_system.customer_service.repository.CustomerRepository;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
@EnableDiscoveryClient
public class CustomerServiceApplication {
	public static void main(String[] args) {
		SpringApplication.run(CustomerServiceApplication.class, args);
	}

	@Bean
	CommandLineRunner run(CustomerRepository repository) {
		return args -> {
			repository.save(new Customer("John Doe", "john.doe@example.com"));
		};
	}
}
