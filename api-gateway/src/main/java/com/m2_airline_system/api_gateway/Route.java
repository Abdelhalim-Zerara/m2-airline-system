package com.m2_airline_system.api_gateway;

import org.springframework.cloud.gateway.server.mvc.handler.GatewayRouterFunctions;
import org.springframework.cloud.gateway.server.mvc.handler.HandlerFunctions;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.function.RequestPredicates;
import org.springframework.web.servlet.function.RouterFunction;
import org.springframework.web.servlet.function.ServerResponse;

@Configuration
public class Route {
       @Bean
    public RouterFunction<ServerResponse> searchServiceRoute(){
        return GatewayRouterFunctions.route("search-service")
                .route(RequestPredicates.path("/search/**"), HandlerFunctions.http("http://localhost:8083"))
                .build();
    }

    @Bean
    public RouterFunction<ServerResponse> bookingServiceRoute(){
        return GatewayRouterFunctions.route("booking-service")
                .route(RequestPredicates.path("/bookings/**"), HandlerFunctions.http("http://localhost:8084"))
                .build();
    }

    @Bean
    public RouterFunction<ServerResponse> customerServiceRoute(){
        return GatewayRouterFunctions.route("customer-service")
                .route(RequestPredicates.path("/customers/**"), HandlerFunctions.http("http://localhost:8085"))
                .build();
    }

    @Bean
    public RouterFunction<ServerResponse> checkinServiceRoute(){
        return GatewayRouterFunctions.route("checkin-service")
                .route(RequestPredicates.path("/checkin/**"), HandlerFunctions.http("http://localhost:8086"))
                .build();
    }

}
