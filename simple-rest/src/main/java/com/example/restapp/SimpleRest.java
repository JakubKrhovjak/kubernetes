package com.example.restapp;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.condition.ConditionalOnCloudPlatform;
import org.springframework.boot.cloud.CloudPlatform;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.core.env.Environment;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;

@RestController
@SpringBootApplication
@RequiredArgsConstructor
public class SimpleRest {

    public static void main(String[] args) {
        SpringApplication.run(SimpleRest.class, args);
    }

    @GetMapping("/test")
    public String test() {
        return "simple-rest app";
    }

    @Value("${message:nula}")
    private String message;


    private final Environment environment;

    @ConditionalOnCloudPlatform(CloudPlatform.KUBERNETES)
    @EventListener({ ApplicationReadyEvent.class})
    public void begin() {
        System.out.println(this.environment.getProperty("message"));
        System.out.println(" @Value: " + message);
    }

}
