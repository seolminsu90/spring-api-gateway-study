package com.gateway.route;

import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RouterA {
    @Bean
    public RouteLocator routeConfigA(RouteLocatorBuilder builder) {
        String httpUri = "http://localhost:8080";
        return builder.routes()
            .route(p -> p
                .path("/items")         //Path prediate
                .filters(f -> f.addRequestHeader("headerKey", "headerValue"))
                .uri(httpUri))
            .route(p -> p
                .host("*.example.com")  //Host predicate
                .filters(f -> f
                    .hystrix(config -> config
                        .setName("mycmd")
                        .setFallbackUri("forward:/fallback")))
                .uri(httpUri))
            .build();
    }
}
