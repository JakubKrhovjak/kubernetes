package com.example.webflux.security;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.ReactiveAuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.WebFilter;
import org.springframework.web.server.WebFilterChain;

import java.util.Collections;
import java.util.List;

import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Mono;

/**
 * Created by Jakub Krhovják on 11/15/22.
 */
@RequiredArgsConstructor
public class TokenFilter implements WebFilter {

    private static final String AUTHORIZATION = "authorization";
    private static final String API_KEY = "api-key";
    private final ReactiveAuthenticationManager authenticationManager;

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, WebFilterChain chain) {
        try {
            var authentication = authenticationManager.authenticate(authorization(exchange.getRequest()));
//            if (authentication.is()) {
//                SecurityContextHolder.getContext().setAuthentication(authentication);
//                return chain.filter(exchange);
//            }
        } catch (Exception e) {
            var response = exchange.getResponse();
//            response.setHeader("WWW-Authenticate", "token-Security");
//            response.sendError(HttpServletResponse.SC_UNAUTHORIZED, HttpStatus.UNAUTHORIZED.getReasonPhrase());
        }

        return Mono.empty();
    }

    private Authentication authorization(ServerHttpRequest request) {
        HttpHeaders headers = request.getHeaders();
        List<String> strings = headers.get(AUTHORIZATION);
//        var headers = Collections.list(request.getHeaderNames());     String header = request.getHeader(AUTHORIZATION);
        if (headers.containsValue(AUTHORIZATION)) {
            return new BearerAuthentication(false, headers.get(AUTHORIZATION));
        }
        return  null;

//        return new ApiTokenAuthentication(false, request.getHeader(API_KEY));
    }
}
