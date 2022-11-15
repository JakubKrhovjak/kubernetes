package com.example.webflux;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import reactor.core.publisher.Mono;

/**
 * Created by Jakub Krhovják on 10/29/22.
 */
@RestController
@RequestMapping("/webflux")
public class TestController {

    @GetMapping("/test")
    public Mono<String> test() {
        return Mono.just("web-flux app test");
    }

    @GetMapping("/basic")
    public Mono<String> basic() {
        return Mono.just("web-flux basic");
    }

    @GetMapping("/token")
    public Mono<String> basic() {
        return Mono.just("web-flux basic");
    }

}
