package com.gateway.route;

import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RouterB {
    @Bean
    public RouteLocator routeConfigB(RouteLocatorBuilder builder) {
        String httpUri = "http://localhost:8080";
        return builder.routes()
            .route(p -> p
                .path("/domains")         //Path prediate
                .filters(f -> f.addRequestHeader("headerKey", "headerValue"))
                .uri(httpUri))
            .build();
    }
}
