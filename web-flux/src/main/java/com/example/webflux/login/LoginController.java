package com.example.webflux.login;

import com.example.webflux.user.User;
import com.example.webflux.user.UserService;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Mono;

/**
 * Created by Jakub Krhovják on 11/22/22.
 */
@RequestMapping("/webflux/auth")
@RestController
@RequiredArgsConstructor
public class LoginController {

    private final UserService userService;

    @PostMapping("login")
    public Mono<ResponseEntity<String>> login(@RequestBody AuthCredentialRequest req) {
        return userService.generateToken(req)
                        .map(token -> ResponseEntity.ok().header("jwt-token", token).build());

    }

    @PostMapping("create")
    public Mono<User> create(@RequestBody AuthCredentialRequest req) {
        return userService.crateUser(req);
    }

}
