package com.example.restapp.login;

import com.example.restapp.user.UserService;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.User;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;

/**
 * Created by Jakub Krhovják on 11/22/22.
 */
@RequestMapping("/simple-rest/auth")
@RestController
@RequiredArgsConstructor
public class LoginController {

    private final UserService  userService;

    @PostMapping("login")
    public ResponseEntity<String> login(@RequestBody AuthCredentialRequest req) {
        return ResponseEntity.ok().header("jwt-token", userService.generateToken(req)).build();
    }

    @PostMapping("create")
    public ResponseEntity<Void> create(@RequestBody AuthCredentialRequest req) {
        userService.crateUser(req);
        return ResponseEntity.ok().build();
    }

}
