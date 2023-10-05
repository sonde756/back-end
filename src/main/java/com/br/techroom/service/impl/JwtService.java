package com.br.techroom.service.impl;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.stereotype.Service;

import java.nio.charset.StandardCharsets;
import java.sql.Date;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Map;

/**
 * service to manager the Jwt Token from the application.
 * @Author Victor Vilar
 */
@Service
public class JwtService {


    /**
     * this SECRET KEY WILL BE REPLACED IN PRODUCTIION******
     */
    private static final String SECRET_KEY =  "techroom application secrect key for tests.";


    private static final String ISSUER = "Techroom";

    /**
     * EXPIRATION TIME FOR THE TOKEN
     */
    private static final int TOKEN_EXPIRATION_TIME = 10;


    /**
     * generate the token
     * @return a jws token
     */
    public String generateToken(Map<String, Object> claims){

        //getting time to set the IssuedAt
        Instant now = Instant.now();

        return  Jwts
                .builder()
                .setIssuer(ISSUER)
                .setSubject("")
                .setClaims(claims)
                .setIssuedAt(Date.from(now))
                .setExpiration(Date.from(now.plus(TOKEN_EXPIRATION_TIME, ChronoUnit.MINUTES)))
                .signWith(Keys.hmacShaKeyFor(SECRET_KEY.getBytes(StandardCharsets.UTF_8)))

                .compact();
    }

    /**
     * validate the token
     * @param token jws token
     * @return the clains of the token
     */
    public Claims validateToken(String token){

        try{
            return Jwts
                    .parserBuilder()
                    .setSigningKey(Keys.hmacShaKeyFor(SECRET_KEY.getBytes(StandardCharsets.UTF_8)))
                    .build()
                    .parseClaimsJws(token)
                    .getBody();

        }catch(Exception e){
            System.out.println(e.getMessage());
            throw new BadCredentialsException(("Invalid Token "));
        }

    }


}
