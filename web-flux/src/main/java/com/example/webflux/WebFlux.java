package com.example.webflux;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.cloud.context.scope.refresh.RefreshScopeRefreshedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.core.env.Environment;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Mono;

@SpringBootApplication
@RestController
@RequiredArgsConstructor
public class WebFlux {

    public static void main(String[] args) {
        SpringApplication.run(WebFlux.class, args);
    }
    @GetMapping("/test")
    public Mono<String> test() {
        return Mono.just("web-flux app");
    }

    @Value("${message}")
    private String message;


    private final Environment environment;

    @EventListener({ ApplicationReadyEvent.class, RefreshScopeRefreshedEvent.class})
    public void begin() {
        System.out.println(" message: " + this.environment.getProperty("message"));
        System.out.println(" @Value: " + message);
    }
}
