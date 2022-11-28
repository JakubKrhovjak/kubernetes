package com.example.webflux.user;

import org.springframework.data.repository.reactive.ReactiveCrudRepository;

import java.util.Optional;

import reactor.core.publisher.Mono;

/**
 * Created by Jakub Krhovják on 11/21/22.
 */
public interface UserRepository extends ReactiveCrudRepository<User, Long> {

    Mono<User> findByUsername(String username);
}
