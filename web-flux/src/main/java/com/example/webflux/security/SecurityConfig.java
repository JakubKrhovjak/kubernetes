package com.example.webflux.security;


import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.authentication.ReactiveAuthenticationManager;
import org.springframework.security.authentication.ReactiveAuthenticationManagerAdapter;
import org.springframework.security.config.web.server.SecurityWebFiltersOrder;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.web.server.SecurityWebFilterChain;
import org.springframework.security.web.server.authentication.AuthenticationWebFilter;
import org.springframework.security.web.server.util.matcher.ServerWebExchangeMatchers;


import java.util.List;

/**
 * Created by Jakub Krhovják on 11/5/22.
 */
@Configuration
public class SecurityConfig  {

    @Value("${bearer-token}")
    private String bearerToken;

//    @Value("${api-token}")
//    private String apiToken;


//    @Bean
//    public ReactiveAuthenticationManager customAuthManager(ServerHttpSecurity http) throws Exception {
//        var authManager = new ProviderManager(List.of(new BearerAuthenticationProvider(bearerToken)));
//        return new ReactiveAuthenticationManagerAdapter(authManager);
//    }


    @Bean
    @Order(1)
    public SecurityWebFilterChain tokenFilterChain(ServerHttpSecurity http) throws Exception {
        var authManager = new ProviderManager(List.of(new BearerAuthenticationProvider(bearerToken)));

        return http
                .securityMatcher(ServerWebExchangeMatchers.pathMatchers("/webflux/token/**"))
                .addFilterAt(new TokenFilter(new ReactiveAuthenticationManagerAdapter(authManager)), SecurityWebFiltersOrder.AUTHENTICATION)
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
//    @Bean
//    public PasswordEncoder passwordEncoder() {
//        return new BCryptPasswordEncoder();
//    }
}
