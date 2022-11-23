package com.example.restapp.security.auth;

import com.example.restapp.security.JwtService;
import com.example.restapp.user.UserRepository;

import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;

import lombok.RequiredArgsConstructor;

/**
 * Created by Jakub Krhovják on 11/13/22.
 */

@RequiredArgsConstructor
public class ApiTokenAuthenticationProvider implements AuthenticationProvider {

    private final String apiToken;

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        var jwtToken = authentication.getCredentials().toString();
        if(apiToken.equals(authentication.getCredentials())) {
            return new ApiTokenAuthentication(true, null);
        }

        throw new BadCredentialsException("");
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return ApiTokenAuthentication.class.equals(authentication);
    }
}
