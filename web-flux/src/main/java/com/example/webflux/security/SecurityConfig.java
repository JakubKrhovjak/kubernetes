package com.example.webflux.security;

import com.fasterxml.jackson.core.filter.TokenFilter;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.DelegatingReactiveAuthenticationManager;
import org.springframework.security.authentication.ReactiveAuthenticationManager;
import org.springframework.security.authentication.ReactiveAuthenticationManagerAdapter;
import org.springframework.security.config.annotation.SecurityConfigurerAdapter;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.core.userdetails.MapReactiveUserDetailsService;
import org.springframework.security.core.userdetails.ReactiveUserDetailsService;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.server.SecurityWebFilterChain;
import org.springframework.security.web.server.authentication.AuthenticationWebFilter;
import org.springframework.security.web.server.util.matcher.ServerWebExchangeMatchers;
import org.springframework.security.web.servlet.util.matcher.MvcRequestMatcher;

/**
 * Created by Jakub Krhovják on 11/5/22.
 */
@Configuration
public class SecurityConfig  {

    @Value("${bearer-token}")
    private String bearerToken;

//    @Value("${api-token}")
//    private String apiToken;


    public ReactiveAuthenticationManagerAdapter customAuthManager(HttpSecurity http) throws Exception {
        var authManager = http.getSharedObject(AuthenticationManagerBuilder.class)
                .authenticationProvider(new BearerAuthenticationProvider(bearerToken))
//                .authenticationProvider(new ApiTokenAuthenticationProvider(apiToken))
                .build();

        return new ReactiveAuthenticationManagerAdapter(authManager);
    }


    @Bean
    @Order(2)
    public SecurityWebFilterChain tokenFilterChain(ServerHttpSecurity http) throws Exception {
        return http
                .securityMatcher(ServerWebExchangeMatchers.pathMatchers("/webflux/token/**"))
//                .addFilterAt(new TokenFilter(customAuthManager(http)), AuthenticationWebFilter.class)
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
