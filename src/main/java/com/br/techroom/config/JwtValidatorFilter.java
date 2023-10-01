package com.br.techroom.config;

import com.br.techroom.service.impl.JwtService;
import io.jsonwebtoken.Claims;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;


/**
 * Filter to get the token and validate it. This filter is placed before the BasicAuthenticationFilter.
 * If the request url it is the login enpoint this filter will not filter.
 */
public class JwtValidatorFilter extends OncePerRequestFilter {

    private static final String AUTHORIZATION_HEADER = "Authorization";
    private static final String BEARER_HEADER = "Bearer ";


    private JwtService jwtService;

    @Autowired
    public JwtValidatorFilter(JwtService jwtService){
        this.jwtService = jwtService;
    }


    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        //getting the Authenticaton Header from request
        String jwt = request.getHeader(AUTHORIZATION_HEADER);

        // if the token is null then the filterchain do filter and return
        if(jwt == null){
            filterChain.doFilter(request,response);
            return;
        }

        //if the jwt does not start with 'Bearer ' then filterchain do filer and return
        if(!jwt.startsWith(BEARER_HEADER)){
            filterChain.doFilter(request,response);
            return;
        }

        //replace 'Bearer ' for ''
        jwt = jwt.replace(BEARER_HEADER,"");

        //try to validate the jwt,
        Claims claims = this.jwtService.validateToken(jwt);

        //if the token is valid then creates a new Authentication
        Authentication auth = new UsernamePasswordAuthenticationToken(claims.get("username"),null,null);

        //putting the Authentication inside security context holder.
        SecurityContextHolder.getContext().setAuthentication(auth);

        filterChain.doFilter(request,response);

    }

    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) throws ServletException {
        return request.getRequestURL().equals("/api/v1/login") || request.getRequestURL().equals("/api/v1/register");
    }

}
