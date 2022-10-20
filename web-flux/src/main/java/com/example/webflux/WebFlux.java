package com.example.webflux;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import reactor.core.publisher.Mono;

@SpringBootApplication
@RestController
public class WebFlux {

    public static void main(String[] args) {
        SpringApplication.run(WebFlux.class, args);
    }
    @GetMapping("/test")
    public Mono<String> test() {
        return Mono.just("web-flux app");
    }

}
