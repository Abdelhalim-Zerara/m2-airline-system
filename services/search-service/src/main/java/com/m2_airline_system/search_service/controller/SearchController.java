package com.m2_airline_system.search_service.controller;

import com.m2_airline_system.search_service.dto.FlightSearchResultDTO;
import com.m2_airline_system.search_service.service.SearchService;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/search")
public class SearchController {
    private final SearchService searchService;

    public SearchController(SearchService searchService) {
        this.searchService = searchService;
    }

    @GetMapping
    public List<FlightSearchResultDTO> searchFlights(@RequestParam String origin, @RequestParam String destination) {
        return searchService.search(origin, destination);
    }
}