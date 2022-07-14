package com.mc.demo.ergon.services.security;

import java.util.Map;

import org.joda.time.DateTime;
import org.springframework.beans.factory.BeanInitializationException;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTCreator;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;

@Component
public class JwtProvider {

    public static String secret;
    public static String issuer;
    public static String audience;
    public static int expireSeconds;

    public JwtProvider(Environment env) {
        JwtProvider.secret = env.getProperty("security.secret");
        JwtProvider.issuer = env.getProperty("security.issuer");
        JwtProvider.audience = env.getProperty("security.audience");
        JwtProvider.expireSeconds = Integer.parseInt(env.getProperty("security.expireSeconds"));
        
        if (JwtProvider.secret == null || JwtProvider.issuer == null || JwtProvider.audience == null || JwtProvider.expireSeconds == 0) {
            throw new BeanInitializationException("Cannot assign security properties. Check application.yml file.");
        }
    }

    public static String createJwt(String subject, Map<String, Object> payloadClaims) {
        final DateTime now = DateTime.now();

        JWTCreator.Builder builder = JWT
            .create()
            .withSubject(subject)
            .withIssuer(JwtProvider.issuer)
            .withIssuedAt(now.toDate())
            .withExpiresAt(now.plusSeconds(JwtProvider.expireSeconds).toDate())
            .withAudience(JwtProvider.audience);
        
        for (Map.Entry<String, Object> entry : payloadClaims.entrySet()) {
            builder.withClaim(entry.getKey(), entry.getValue().toString());
        }
        
        return builder.sign(Algorithm.HMAC256(JwtProvider.secret));
    }

    public static DecodedJWT verifyJwt(String jwt) {
        return JWT.require(Algorithm.HMAC256(JwtProvider.secret)).build().verify(jwt);
    }
}