package com.example.webflux.security;


import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.authentication.ReactiveAuthenticationManager;
import org.springframework.security.config.web.server.SecurityWebFiltersOrder;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.server.SecurityWebFilterChain;
import org.springframework.security.web.server.authentication.AuthenticationWebFilter;
import org.springframework.security.web.server.util.matcher.ServerWebExchangeMatchers;

import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Mono;

/**
 * Created by Jakub Krhovják on 11/5/22.
 */
@Configuration
@RequiredArgsConstructor
public class SecurityConfig  {

    private static final String AUTHORIZATION = "Authorization";

    private static final String API_KEY = "api-key";

    private static final String JWT_TOKEN = "jwt-token";

    @Value("${bearer-token}")
    private String bearerToken;

    @Value("${api-token}")
    private String apiToken;

    private final JwtService jwtService;


    public ReactiveAuthenticationManager emptyReactiveAuthenticationManager() {
        return Mono::justOrEmpty;
    }

    public AuthenticationWebFilter bearerFilter() {
        var filter = new AuthenticationWebFilter(emptyReactiveAuthenticationManager());
        filter.setServerAuthenticationConverter(exchange -> Mono.justOrEmpty(exchange)
                .mapNotNull(ex -> ex.getRequest().getHeaders().getFirst(AUTHORIZATION))
                .filter(value -> bearerToken.equals(parseToken(value)))
                .map(value -> new TokenAuthentication(true, value)));

        return filter;
    }

    public AuthenticationWebFilter apiTokenFilter() {
        var filter = new AuthenticationWebFilter(emptyReactiveAuthenticationManager());
        filter.setServerAuthenticationConverter(exchange -> Mono.justOrEmpty(exchange)
                .mapNotNull(ex -> ex.getRequest().getHeaders().getFirst(JWT_TOKEN))
                .flatMap(value -> {
                    return jwtService.validateToken(value).map(v ->  {
                        return new TokenAuthentication(v, value);
                    });
                }));

        return filter;
    }

    public AuthenticationWebFilter jwtTokenFilter() {
        var filter = new AuthenticationWebFilter(emptyReactiveAuthenticationManager());
        filter.setServerAuthenticationConverter(exchange -> Mono.justOrEmpty(exchange)
                .mapNotNull(ex -> ex.getRequest().getHeaders().getFirst(API_KEY))
                .filter(value -> apiToken.equals(value))
                .map(value -> new TokenAuthentication(true, value)));

        return filter;
    }

    private static String parseToken(String bearerToken) {
        return bearerToken.replace("Bearer", "").trim();
    }

    @Bean
    @Order(1)
    public SecurityWebFilterChain tokenFilterChain(ServerHttpSecurity http) throws Exception {
        return http
                .securityMatcher(ServerWebExchangeMatchers.pathMatchers("/webflux/token/**"))
                .addFilterAt(bearerFilter(), SecurityWebFiltersOrder.AUTHENTICATION)
                .addFilterAt(apiTokenFilter(), SecurityWebFiltersOrder.AUTHENTICATION)
                .addFilterAt(jwtTokenFilter(), SecurityWebFiltersOrder.AUTHENTICATION)
                .authorizeExchange().pathMatchers("/webflux/token/**").authenticated()
                .and()
                .build();
    }



    @Bean
    @Order(2)
    public SecurityWebFilterChain basicFilterChain(ServerHttpSecurity http) throws Exception {
        return http
                .securityMatcher(ServerWebExchangeMatchers.pathMatchers("/webflux/basic/**"))
                .httpBasic()
                .and()
                .authorizeExchange().pathMatchers("/webflux/basic/**").hasAuthority("ROLE_test")
                .and()
                .build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

//    @Bean
//    public ReactiveUserDetailsService userDetailsService() {
//        var user = User.withUsername("jakub")
//                .password(passwordEncoder().encode("123"))
//                .authorities("test")
//                .build();
//
//        return new MapReactiveUserDetailsService(user);
//
//    }
//

}
