package com.example.restapp;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by Jakub Krhovják on 10/29/22.
 */


@RestController
@RequestMapping("/simple-rest")
public class TestController {

    @GetMapping("/basic")
    public String test() {
        return "simple-rest app basic";
    }

    @GetMapping("/token")
    public String token() {
        return "simple-rest app token";
    }


}
