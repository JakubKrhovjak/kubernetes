package com.example.webflux.login;

import lombok.Data;

/**
 * Created by Jakub Krhovják on 11/22/22.
 */

@Data
public class AuthCredentialRequest {

    private String username;

    private String password;
}
