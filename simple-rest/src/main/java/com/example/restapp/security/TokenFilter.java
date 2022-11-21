package com.example.restapp.security;

import com.example.restapp.security.auth.ApiTokenAuthentication;
import com.example.restapp.security.auth.BearerAuthentication;

import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Collections;

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
