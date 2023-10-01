package com.br.techroom.service.impl;

import com.br.techroom.dto.requests.LoginRequestDTO;
import com.br.techroom.dto.responses.LoginResponseDTO;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import lombok.extern.java.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class AccountServiceImpl {

    @Autowired
    private AuthenticationManager auhtenticationManager;
    @Autowired
    private JwtService jwtService;

    public LoginResponseDTO attemptAuthentication(LoginRequestDTO loginRequestDto){


        //attempt to authenticate the login request
        Authentication auth = this.auhtenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginRequestDto.getUsername(),loginRequestDto.getPassword())
        );

        //if there is no error then
        //create a new login response dto
        LoginResponseDTO loginResponseDto = new LoginResponseDTO();

        //setting name
        loginResponseDto.setUsername(auth.getName());

        //creating the claims for the token and return the token
        loginResponseDto.setToken(this.jwtService.generateToken(this.createClaims(auth)));

        return loginResponseDto;
    }

    /**
     * create claims to the jwt
     * @param authentication
     * @return
     */
    private Map<String,Object> createClaims(Authentication authentication){
        Map<String,Object> claims = new HashMap<>();
        claims.put("username",authentication.getName());

        return claims;

    }


}
