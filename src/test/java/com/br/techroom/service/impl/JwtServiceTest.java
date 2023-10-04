package com.br.techroom.service.impl;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * test for the JwtService class
 * @author Victor Vilar
 */
class JwtServiceTest {

    String token;

    @BeforeEach
    void setUp(){
        token = "sjlksa0gjgjgjfg04g9jbklsj04-.8398740fsadf9a8s4thv.92387098";
    }

    @Test
    void generateTokenSuccesfully() {
    }

    @Test
    void validateToken() {
    }

}