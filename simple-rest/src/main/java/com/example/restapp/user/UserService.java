package com.example.restapp.user;

import com.example.restapp.login.AuthCredentialRequest;
import com.example.restapp.security.JwtService;

import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

/**
 * Created by Jakub Krhovják on 11/22/22.
 */

@Service
@RequiredArgsConstructor
public class UserService {

    private final PasswordEncoder passwordEncoder;

    private final UserRepository userRepository;

    private final JwtService jwtService;

    public String generateToken(AuthCredentialRequest req) {
        return userRepository.findByUsername(req.getUsername())
                .filter(user -> passwordEncoder.matches(req.getPassword(), user.getPassword()))
                .map(jwtService::generateToken)
                .orElseThrow(() -> new BadCredentialsException(""));
    }

    public void crateUser(AuthCredentialRequest req) {
        var newUser = new User().setUsername(req.getUsername()).setId(1)
                .setPassword(passwordEncoder.encode(req.getPassword()));
        userRepository.save(newUser);
    }
}
