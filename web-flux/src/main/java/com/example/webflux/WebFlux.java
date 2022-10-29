package com.example.webflux;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import lombok.RequiredArgsConstructor;

@SpringBootApplication
@RequiredArgsConstructor
public class WebFlux {

    public static void main(String[] args) {
        SpringApplication.run(WebFlux.class, args);
    }

//    @Value("${message}")
//    private String message;
//
//    private final Environment environment;
//
//    @EventListener({ ApplicationReadyEvent.class })
//    public void begin() {
//        System.out.println(" message: " + this.environment.getProperty("message"));
//        System.out.println(" @Value: " + message);
//    }
}
