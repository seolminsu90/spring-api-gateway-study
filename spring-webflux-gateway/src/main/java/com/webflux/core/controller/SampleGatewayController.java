package com.webflux.core.controller;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.reactive.function.client.WebClient;

import com.webflux.core.domain.People;

import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Slf4j
@RequestMapping("/api")
@RestController
public class SampleGatewayController {
    private final WebClient webClient;

    public SampleGatewayController(WebClient.Builder builder) {
        this.webClient = builder
                .filter((request, next) -> next.exchange(request))
                .defaultHeader("foo", "bar")
                .baseUrl("http://localhost:8080")
                .build();
    }

    // 0 - 1 개
    @GetMapping("/v1/people")
    public Mono<People> findByAge1() {
        log.info("Sample Api Gateway /v1/people");
        return webClient.get()
                .uri("/people/1")
                .retrieve()
                .bodyToMono(People.class);
    }

    // Response 세부
    @GetMapping("/v2/people")
    public Mono<People> findByAge2() {
        log.info("Sample Api Gateway /v2/people");
        return webClient.get()
                .uri("/people/1")
                .exchange()
                .flatMap(it -> {
                    if(it.statusCode() == HttpStatus.NOT_FOUND) {
                        throw new RuntimeException("..add exception..");
                    }
                    return it.bodyToMono(People.class);
                });
    }

    // 0 - N 개
    @GetMapping("/v3/people")
    public Flux<People> findAll() {
        log.info("Sample Api Gateway /v3/people");
        return webClient.get()
                .uri("/people")
                .retrieve()
                .bodyToFlux(People.class);
    }
}
