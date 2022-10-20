package com.example.restapp;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@SpringBootApplication
public class SimpleRest {

    public static void main(String[] args) {
        SpringApplication.run(SimpleRest.class, args);
    }

    @GetMapping("/test")
    public String test() {
        return "simple-rest app";
    }

}
