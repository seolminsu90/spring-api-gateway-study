package com.gateway.token;

import com.auth0.jwt.exceptions.JWTVerificationException;

public class TokenException extends JWTVerificationException {

    public TokenException(String message) {
        super(message);
    }
}
