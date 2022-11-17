package com.example.webflux.security;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.ReactiveAuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.ReactiveSecurityContextHolder;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.WebFilter;
import org.springframework.web.server.WebFilterChain;

import java.util.Collections;
import java.util.List;

import io.netty.util.internal.StringUtil;
import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Mono;

/**
 * Created by Jakub Krhovják on 11/15/22.
 */
@RequiredArgsConstructor
public class TokenFilter implements WebFilter {

    private static final String AUTHORIZATION = "Authorization";
    private static final String API_KEY = "api-key";
    private final ReactiveAuthenticationManager authenticationManager;

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, WebFilterChain chain) {

//     return authenticationManager.authenticate(authorization(exchange.getRequest()))
//                .doOnNext(authentication -> {
//                    ReactiveSecurityContextHolder.getContext()
//                            .subscriberContext((ReactiveSecurityContextHolder.withAuthentication(authentication)));
//                    chain.filter(exchange);
//                    return Mono.empty();
//                })
//             .onErrorMap(e -> {
//                 exchange.getResponse().setStatusCode(HttpStatus.UNAUTHORIZED);
//                 exchange.getResponse().getHeaders().set(HttpHeaders.WWW_AUTHENTICATE, "Bearer");
//                 return Mono.empty();
//             });

       return Mono.empty();
    }

    private static Authentication authorization(ServerHttpRequest request) {
        var headers = request.getHeaders();
        if (headers.containsKey(AUTHORIZATION)) {
            return new BearerAuthentication(false, headers.get(AUTHORIZATION).stream().findFirst().orElse(StringUtil.EMPTY_STRING));
        }
        return  null;

//        return new ApiTokenAuthentication(false, request.getHeader(API_KEY));
    }
}
