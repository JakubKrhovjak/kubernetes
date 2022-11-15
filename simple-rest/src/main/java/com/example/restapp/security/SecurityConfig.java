package com.example.restapp.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.core.annotation.Order;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.SecurityConfigurerAdapter;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.preauth.x509.X509AuthenticationFilter;
import org.springframework.security.web.servlet.util.matcher.MvcRequestMatcher;
import org.springframework.web.servlet.handler.HandlerMappingIntrospector;

import java.util.List;

/**
 * Created by Jakub Krhovják on 11/5/22.
 */
@Configuration
public class SecurityConfig {

    @Value("${bearer-token}")
    private String bearerToken;

    @Value("${api-token}")
    private String apiToken;

    @Bean
    public AuthenticationManager customAuthManager(HttpSecurity http) throws Exception {
        return http.getSharedObject(AuthenticationManagerBuilder.class)
                .authenticationProvider(new BearerAuthenticationProvider(bearerToken))
                .authenticationProvider(new ApiTokenAuthenticationProvider(apiToken))
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
