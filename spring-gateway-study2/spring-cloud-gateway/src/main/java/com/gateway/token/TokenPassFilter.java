package com.gateway.token;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractNameValueGatewayFilterFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;

import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;

import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Mono;

@Slf4j
@Component
public class TokenPassFilter extends AbstractNameValueGatewayFilterFactory {

    private static final String WWW_AUTH_HEADER = "WWW-Authenticate";
    private static final String X_JWT_SUB_HEADER = "X-jwt-sub";

    @Override
    public GatewayFilter apply(NameValueConfig config) {
        return (exchange, chain) -> {

            try {
                String token = this.extractTokenTest(exchange.getRequest());

                log.info("---- 검증 시작... ----");
                // 여기에 검증 Process 적용
                // VerifyToken(token)......
                log.info("---- 검증 끝... ----");

                ServerHttpRequest request = exchange.getRequest().mutate().headers(header -> {
                    header.set(X_JWT_SUB_HEADER, token);
                }).build();

                return chain.filter(exchange.mutate().request(request).build());

            } catch (JWTVerificationException ex) {

                log.error(ex.toString());
                return this.onError(exchange, ex.getMessage());
            }
        };
    }

    private Mono<Void> onError(ServerWebExchange exchange, String err) {
        ServerHttpResponse response = exchange.getResponse();
        response.setStatusCode(HttpStatus.UNAUTHORIZED);
        response.getHeaders().add(WWW_AUTH_HEADER, this.formatErrorMsg(err));

        return response.setComplete();
    }

    private String extractTokenTest(ServerHttpRequest request) {
        return "token";
    }

    private String extractToken(ServerHttpRequest request) {
        if (!request.getHeaders().containsKey("Authorization")) {
            throw new TokenException("Authorization header is missing");
        }

        List<String> headers = request.getHeaders().get("Authorization");
        if (headers.isEmpty()) {
            throw new TokenException("Authorization header is empty");
        }

        String credential = headers.get(0).trim();
        String[] components = credential.split("\\s");

        if (components.length != 2) {
            throw new TokenException("Malformat Authorization content");
        }

        if (!components[0].equals("Bearer")) {
            throw new TokenException("Bearer is needed");
        }

        return components[1].trim();
    }

    private String formatErrorMsg(String msg) {
        return String.format("Bearer realm=\"acm.com\", " + "error=\"https://tools.ietf.org/html/rfc7519\", "
                + "error_description=\"%s\" ", msg);
    }
}