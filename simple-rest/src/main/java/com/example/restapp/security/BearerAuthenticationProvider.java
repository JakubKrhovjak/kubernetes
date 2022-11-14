package com.example.restapp.security;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;

/**
 * Created by Jakub Krhovják on 11/5/22.
 */

//@Component
@RequiredArgsConstructor
public class BearerAuthenticationProvider implements AuthenticationProvider {

    private final String bearerToken;

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {

      if(bearerToken.equals(parseToken(authentication.getCredentials().toString()))) {
          return new BearerAuthentication(true, null);
      }

      throw new BadCredentialsException("");
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return BearerAuthentication.class.equals(authentication);
    }

    private static String parseToken(String bearerToken) {
        return bearerToken.replace("Bearer", "").trim();
    }
}
