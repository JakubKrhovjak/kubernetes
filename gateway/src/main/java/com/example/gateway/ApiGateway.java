package com.example.gateway;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@SpringBootApplication
public class ApiGateway {

    public static void main(String[] args) {
        SpringApplication.run(ApiGateway.class, args);
    }

    @GetMapping()
    public String ok() {
        return "ok";
    }
    //
    @Bean
    public RouteLocator myRoutes(RouteLocatorBuilder builder) {
        return builder.routes()
                .route("simple-rest", p -> p
                        .path("/simple-rest/**")
                        .uri("http://localhost:8080/"))
                .route("simple-rest", p -> p
                        .path("/webflux/**")
                        .uri("http://localhost:8081/"))
                .build();
    }


}
