package com.example.restapp;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by Jakub Krhovják on 10/29/22.
 */


@CrossOrigin("*")
@RestController
@RequestMapping("/simple-rest")
public class TestController {

    @GetMapping("/basic")
    public String basic() {
        return "simple-rest app basic";
    }

    @GetMapping("/token")
    public String token() {
        return "simple-rest app token";
    }

    @GetMapping("/test")
    public String test() {
        return "simple-rest app test";
    }

    @GetMapping("/jwt")
    public String jwt() {
        return "simple-rest app jwt";
    }



}
