package com.br.techroom.service;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTCreator;
import com.auth0.jwt.algorithms.Algorithm;
import com.br.techroom.entities.Account;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;

@Service
public class JwtService {

    @Value("${jwt.config.key.secret}")
    private String secret;

    @Value("${jwt.config.key.issuer}")
    private String issuer;

    /**
     * Gera o token Jwt
     *
     * @param account Objeto Account contendo uma entidade representando o usuário
     * @return Token Jwt com a signature
     */
    public String generateToken(Account account) {
        JWTCreator.Builder jwt = JWT.create()
                .withIssuer(issuer)
                .withSubject(account.getEmail())
                .withIssuedAt(Instant.now())
                .withExpiresAt(generateExpirationDateToken());

        account.getAuthorities().forEach(auth -> jwt.withClaim("authority", auth.getAuthority()));

        return jwt.sign(algorithm());
    }

    /**
     * Valida o token Jwt
     *
     * @param token Token Jwt em String
     * @return O username do usuário (desde que o token seja válido)
     */
    public String validateToken(String token) {
        return JWT.require(algorithm())
                .withIssuer(issuer)
                .build()
                .verify(token)
                .getSubject();
    }

    /**
     * Gera um tempo de expiração
     *
     * @return Um objeto Instant
     */
    public Instant generateExpirationDateToken() {
        return LocalDateTime.now().plusHours(10).toInstant(ZoneOffset.of("-03:00"));
    }

    /**
     * Gera um objeto Algorithm baseado na secret key
     *
     * @return Um objeto Algorithm
     */
    private Algorithm algorithm() {
        return Algorithm.HMAC256(secret);
    }
}