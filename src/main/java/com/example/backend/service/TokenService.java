package com.example.backend.service;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.example.backend.entity.User;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class TokenService {
    @Value("${app.token.secret}")
    private String secret;

    @Value("${app.token.issuer}")
    private String issuer;

    public String tokenize(User user) {

        Date expireAt = new Date();
        expireAt.setDate(expireAt.getDate() + 1);

        Algorithm algorithm = Algorithm.HMAC256(secret);
        return JWT.create()
                .withIssuer(issuer)
                .withClaim("principal", user.getId())
                .withClaim("role", "USER")
                .withExpiresAt(expireAt)
                .sign(algorithm);
    }

    public DecodedJWT verify(String token) {

        try {
            Algorithm algorithm = Algorithm.HMAC256(secret);
            JWTVerifier verifier = JWT.require(algorithm)
                    // specify any specific claim validations
                    .withIssuer(issuer)
                    .build();

           return verifier.verify(token);
        } catch (Exception e) {

            return null;
        }
    }

}
