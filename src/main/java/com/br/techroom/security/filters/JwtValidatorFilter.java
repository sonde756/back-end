package com.br.techroom.security.filters;

import com.br.techroom.repository.AccountRepository;
import com.br.techroom.service.JwtService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
public class JwtValidatorFilter extends OncePerRequestFilter {

    @Autowired
    private JwtService jwtService;

    @Autowired
    private AccountRepository repository;

    @Override
    protected void doFilterInternal(
            HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {

        String tokenJwt = getTokenOnHeader(request);

        if (tokenJwt != null) {
            String login = jwtService.validateToken(tokenJwt);
            UserDetails user = repository.findByUsername(login);

            SecurityContextHolder.getContext().setAuthentication(
                    new UsernamePasswordAuthenticationToken(user, null, user.getAuthorities()));
        }
        filterChain.doFilter(request, response);
    }

    private String getTokenOnHeader(HttpServletRequest request) {
        return (request.getHeader("Authorization") != null)
                ? request.getHeader("Authorization").split(" ")[1]
                : null;
    }

    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) {
        String url = request.getRequestURL().toString();
        return url.equals("/api/v1/login")
               || url.equals("/api/v1/register")
               || url.contains("/swagger-ui/")
               || url.contains("/v2/api-docs");
    }
}