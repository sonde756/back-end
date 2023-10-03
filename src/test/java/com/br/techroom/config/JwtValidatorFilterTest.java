package com.br.techroom.config;

import com.br.techroom.service.impl.JwtService;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.springframework.security.core.context.SecurityContextHolder;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class JwtValidatorFilterTest {


    @InjectMocks
    JwtValidatorFilter filter;

    @Mock
    JwtService jwtService;

    String token ;
    String notBearerToken ;
    String registerUrl;
    String loginUrl;
    Claims claims;






    @BeforeEach
    void setUp(){
        token = "Bearer 93HHDfdshjdf&*G";
        notBearerToken = "Basic 93HHDfdshjdf&*G";
        claims = Jwts.claims(Map.of("username","techroom"));
        SecurityContextHolder.getContext().setAuthentication(null);
        registerUrl = "/api/v1/register";
        loginUrl = "/api/v1/login";

    }

    @Test
    void doFilterInternalDoNotCheckTokenIfTheAuthorizationHeaderIsNull() throws Exception {
        HttpServletRequest request = mock(HttpServletRequest.class);
        HttpServletResponse response = mock(HttpServletResponse.class);
        FilterChain chain = mock(FilterChain.class);
        when(request.getHeader(any())).thenReturn(null);

        filter.doFilterInternal(request,response,chain);
        assertNull(SecurityContextHolder.getContext().getAuthentication());
        verify(chain).doFilter(any(ServletRequest.class), any(ServletResponse.class));

    }

    @Test
    void doFilterInternalDoNotCheckTokenIfTheAuthorizationHeaderNotBeginWithBearer() throws Exception {
        HttpServletRequest request = mock(HttpServletRequest.class);
        HttpServletResponse response = mock(HttpServletResponse.class);
        FilterChain chain = mock(FilterChain.class);
        when(request.getHeader(JwtValidatorFilter.AUTHORIZATION_HEADER)).thenReturn(notBearerToken);

        filter.doFilterInternal(request,response,chain);
        assertNull(SecurityContextHolder.getContext().getAuthentication());
        verify(chain).doFilter(any(ServletRequest.class), any(ServletResponse.class));

    }



    @Test
    void doFilterInternalSuccessfullyAuthenticationWhenPassValidToken() throws Exception {
        HttpServletRequest request = mock(HttpServletRequest.class);
        HttpServletResponse response = mock(HttpServletResponse.class);

        FilterChain chain = mock(FilterChain.class);

        when(request.getHeader(JwtValidatorFilter.AUTHORIZATION_HEADER)).thenReturn(token);
        when(this.jwtService.validateToken(any())).thenReturn(claims);
        filter.doFilterInternal(request,response,chain);
        assertNotNull(SecurityContextHolder.getContext().getAuthentication());
        verify(chain).doFilter(any(ServletRequest.class), any(ServletResponse.class));

    }



}