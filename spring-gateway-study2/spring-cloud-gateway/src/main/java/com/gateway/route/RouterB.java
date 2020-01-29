package com.gateway.route;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.filter.factory.AbstractNameValueGatewayFilterFactory.NameValueConfig;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.gateway.token.TokenPassFilter;

@Configuration
public class RouterB {
    @Autowired
    TokenPassFilter tokenFilter;

    @Bean
    public RouteLocator routeConfigB(RouteLocatorBuilder builder) {
        String httpUri = "http://localhost:8080";
        return builder.routes()
            .route(p -> p
                .path("/domains")             //Path prediate 
                .filters(f -> f.filter(tokenFilter.apply(new NameValueConfig())))
                .uri(httpUri))
            .build();
    }
}
