package com.example.restapp.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import lombok.RequiredArgsConstructor;

/**
 * Created by Jakub Krhovják on 11/5/22.
 */
@RequiredArgsConstructor
public class TokenFilter extends OncePerRequestFilter {

    @Autowired
    AuthenticationManager authenticationManager;


    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        var bearer = request.getHeader("authorization");
        var authentication = authenticationManager.authenticate(new BearerAuthentication(false, bearer));
        if (authentication.isAuthenticated()) {
            filterChain.doFilter(request, response);
        } else {
            throw new BadCredentialsException("");
        }


    }
}
