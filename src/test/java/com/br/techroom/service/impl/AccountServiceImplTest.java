package com.br.techroom.service.impl;

import com.br.techroom.dto.requests.LoginRequestDTO;
import com.br.techroom.dto.responses.LoginResponseDTO;
import org.aspectj.lang.annotation.Before;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class AccountServiceImplTest {

    private LoginRequestDTO loginRequestDTO;
    private Authentication authentication;
    private String token;
    private Map<String,Object> claims;


    @InjectMocks
    AccountServiceImpl accountService;

    @Mock
    private AuthenticationManager auhtenticationManager;

    @Mock
    private JwtService jwtService;

    @BeforeEach
    void setUp(){
        loginRequestDTO = new LoginRequestDTO();
        loginRequestDTO.setUsername("techroom");
        loginRequestDTO.setPassword("techroom");
        authentication = new UsernamePasswordAuthenticationToken(loginRequestDTO.getUsername(),loginRequestDTO.getPassword());
        claims = new HashMap<>();
        claims.put("username",authentication.getName());
        token =
                "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJzdWIiOiIxMjM0NTY3ODkwIiwibmFtZSI6IkpvaG4gRG9lIiwiaWF0IjoxNTE2MjM5MDIyfQ.SflKxwRJSMeKKF2QT4fwpMeJf36POk6yJV_adQssw5c";
    }


    @Test
    void attemptAuthenticationWhenUsernameIsValid() {

        when(auhtenticationManager.authenticate(any(UsernamePasswordAuthenticationToken.class))).thenReturn(authentication);
        when(jwtService.generateToken(claims)).thenReturn(token);
        LoginResponseDTO loginResponseDto = accountService.attemptAuthentication(loginRequestDTO);
        assertEquals(loginResponseDto.getUsername(), loginRequestDTO.getUsername());
        assertEquals(loginResponseDto.getToken(), token);

    }


}