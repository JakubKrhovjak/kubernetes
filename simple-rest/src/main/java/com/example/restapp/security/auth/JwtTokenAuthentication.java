package com.example.restapp.security.auth;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;

import lombok.RequiredArgsConstructor;

/**
 * Created by Jakub Krhovják on 11/21/22.
 */
@RequiredArgsConstructor
public class JwtTokenAuthentication implements Authentication {

    private final boolean authenticated;

    private final String jwtToken;

    @Override
    public boolean isAuthenticated() {
        return authenticated;
    }
    @Override
    public Object getCredentials() {
        return jwtToken;
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
    public void setAuthenticated(boolean isAuthenticated) throws IllegalArgumentException {

    }

    @Override
    public String getName() {
        return null;
    }
}
