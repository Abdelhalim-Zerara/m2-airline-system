package com.m2_airline_system.search_service.service;

import com.m2_airline_system.search_service.dto.FlightSearchResultDTO;
import com.m2_airline_system.search_service.feign.FlightServiceClient;
import com.m2_airline_system.search_service.feign.InventoryServiceClient;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class SearchService {
    private final FlightServiceClient flightServiceClient;
    private final InventoryServiceClient inventoryServiceClient;

    public SearchService(FlightServiceClient flightServiceClient, InventoryServiceClient inventoryServiceClient) {
        this.flightServiceClient = flightServiceClient;
        this.inventoryServiceClient = inventoryServiceClient;
    }

    public List<FlightSearchResultDTO> search(String origin, String destination) {
        List<FlightServiceClient.FlightDTO> flights = flightServiceClient.searchFlights(origin, destination);

        return flights.stream()
                .map(flight -> {
                    InventoryServiceClient.InventoryDTO inventory = inventoryServiceClient.getInventory(flight.getId());
                    return FlightSearchResultDTO.builder()
                            .flightId(flight.getId())
                            .flightNumber(flight.getFlightNumber())
                            .origin(flight.getOrigin())
                            .destination(flight.getDestination())
                            .availableSeats(inventory.getAvailableSeats())
                            .build();
                })
                .filter(result -> result.getAvailableSeats() > 0)
                .collect(Collectors.toList());
    }
}