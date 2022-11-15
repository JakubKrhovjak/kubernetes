package com.example.webflux.security;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;

import lombok.RequiredArgsConstructor;

/**
 * Created by Jakub Krhovják on 11/5/22.
 */

@RequiredArgsConstructor
public class BearerAuthentication implements Authentication {

    private final boolean authenticated;
    private final String bearerToken;

    @Override
    public boolean isAuthenticated() {
        return authenticated;
    }
    @Override
    public Object getCredentials() {
        return bearerToken;
    }


    @Override
    public void setAuthenticated(boolean isAuthenticated) throws IllegalArgumentException {
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return null;
    }



    @Override
    public Object getDetails() {
        return null;
    }

    @Override
    public Object getPrincipal() {
        return null;
    }



    @Override
    public String getName() {
        return "Authorization";
    }
}
