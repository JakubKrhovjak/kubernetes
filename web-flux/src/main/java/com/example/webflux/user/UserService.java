package com.example.webflux.user;

import com.example.webflux.login.AuthCredentialRequest;
import com.example.webflux.security.JwtService;

import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Mono;

/**
 * Created by Jakub Krhovják on 11/22/22.
 */

@Service
@RequiredArgsConstructor
public class UserService {

    private final PasswordEncoder passwordEncoder;

    private final UserRepository userRepository;

    private final JwtService jwtService;

    public Mono<String> generateToken(AuthCredentialRequest req) {
        return userRepository.findByUsername(req.getUsername())
                .filter(user -> passwordEncoder.matches(req.getPassword(), user.getPassword()))
                .map(jwtService::generateToken);

//                .onErrorMap(() -> new BadCredentialsException(""));
    }

    public Mono<User> crateUser(AuthCredentialRequest req) {
        return Mono.justOrEmpty(new User().setUsername(req.getUsername()).setId(1)
                .setPassword(passwordEncoder.encode(req.getPassword())))
                .flatMap(userRepository::save);

    }
}
