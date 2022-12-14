package com.example.restapp.security;

import com.example.restapp.security.auth.ApiTokenAuthenticationProvider;
import com.example.restapp.security.auth.BearerAuthenticationProvider;
import com.example.restapp.security.auth.JwtAuthenticationProvider;
import com.example.restapp.security.auth.JwtTokenAuthentication;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

/**
 * Created by Jakub Krhovják on 11/5/22.
 */
@Configuration
public class SecurityConfig {

    @Value("${bearer-token}")
    private String bearerToken;

    @Value("${api-token}")
    private String apiToken;

    @Autowired
    private JwtService jwtService;

    @Bean
    public AuthenticationManager customAuthManager(HttpSecurity http) throws Exception {
        return http.getSharedObject(AuthenticationManagerBuilder.class)
                .authenticationProvider(new BearerAuthenticationProvider(bearerToken))
                .authenticationProvider(new ApiTokenAuthenticationProvider(apiToken))
                .authenticationProvider(new JwtAuthenticationProvider(jwtService))
                .build();
    }

    @Bean
    @Order(1)
    public SecurityFilterChain tokenFilterChain(HttpSecurity http) throws Exception {
        return http
                .mvcMatcher("/simple-rest/token/**")
                .addFilterAt(new TokenFilter(customAuthManager(http)), UsernamePasswordAuthenticationFilter.class)
                .authorizeHttpRequests().mvcMatchers("/simple-rest/token/**").authenticated()
                .and()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .build();
    }

    @Bean
    @Order(2)
    public SecurityFilterChain basicFilterChain(HttpSecurity http) throws Exception {
        return http
                .mvcMatcher("/simple-rest/basic/**")
                .httpBasic()
                .and()
                .authorizeHttpRequests().mvcMatchers("/simple-rest/basic/**").hasAuthority("test")
                .and()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .build();
    }

//    @Bean
//    @Order(1)
//    public SecurityFilterChain jwtFilterChain(HttpSecurity http) throws Exception {
//        return http
//                .mvcMatcher("/simple-rest/jwt/**")
//                .addFilterAt(new TokenFilter(customAuthManager(http)), UsernamePasswordAuthenticationFilter.class)
//                .authorizeHttpRequests().mvcMatchers("/simple-rest/jwt/**").authenticated()
//                .and()
//                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
//                .and()
//                .build();
//    }


    @Bean
    public UserDetailsService userDetailsService() {
        var im = new InMemoryUserDetailsManager();
        var user = User.withUsername("jakub")
                .password(passwordEncoder().encode("123"))
                .authorities("test")
                .build();
        im.createUser(user);

        return im;
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
