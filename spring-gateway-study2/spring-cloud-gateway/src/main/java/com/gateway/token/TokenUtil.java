package com.gateway.token;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class TokenUtil {
    private static final String JWT_SECRECT_KEY = "secret_key";

    public static String createToken() throws IllegalArgumentException, UnsupportedEncodingException {
        String JWT_TOKEN = null;

        try {
            Map<String, Object> headerClaims = new HashMap<>();
            headerClaims.put("key", "value");

            JWT_TOKEN = JWT.create().withHeader(headerClaims).sign(Algorithm.HMAC256(JWT_SECRECT_KEY));
        } catch (JWTCreationException e) {
            log.error("생성 오류 : {}", e.getMessage());
        }

        return JWT_TOKEN;
    }

    public static DecodedJWT verifyToken(String token) throws IllegalArgumentException, UnsupportedEncodingException {
        DecodedJWT DECODED_JWT = null;

        try {
            JWTVerifier verifier = JWT.require(Algorithm.HMAC256(JWT_SECRECT_KEY)).build();

            DECODED_JWT = verifier.verify(token);
        } catch (JWTVerificationException e) {
            log.error("검증 오류 : {}", e.getMessage());
        }

        return DECODED_JWT;
    }
}
