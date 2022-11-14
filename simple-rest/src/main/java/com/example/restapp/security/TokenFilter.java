package com.example.restapp.security;

import org.apache.tomcat.util.http.MimeHeaders;
import org.apache.tomcat.util.http.parser.Authorization;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Enumeration;
import java.util.List;
import java.util.Spliterator;
import java.util.stream.Stream;

import javax.security.sasl.AuthenticationException;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import lombok.RequiredArgsConstructor;

/**
 * Created by Jakub Krhovják on 11/5/22.
 */
//@Component

@RequiredArgsConstructor
public class TokenFilter extends OncePerRequestFilter {

    private static final String AUTHORIZATION = "authorization";
    private static final String API_KEY = "api-key";
    private final AuthenticationManager authenticationManager;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        try {
            var authentication = authenticationManager.authenticate(authorization(request));
            if (authentication.isAuthenticated()) {
                SecurityContextHolder.getContext().setAuthentication(authentication);
                filterChain.doFilter(request, response);
            }
        } catch (Exception e) {
            response.setHeader("WWW-Authenticate", "token-Security");
            response.sendError(HttpServletResponse.SC_UNAUTHORIZED, HttpStatus.UNAUTHORIZED.getReasonPhrase());
        }

    }

    private Authentication authorization(HttpServletRequest request) {
        var headers = Collections.list(request.getHeaderNames());
        String header = request.getHeader(AUTHORIZATION);
        if (headers.contains(AUTHORIZATION)) {
            return new BearerAuthentication(false, request.getHeader(AUTHORIZATION));
        }

        return new ApiTokenAuthentication(false, request.getHeader(API_KEY));
    }
}
