package com.example.api_rate_limiter.security;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;

@Component
public class AuthenticationFilter extends OncePerRequestFilter {
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        try{
            String authHeader = request.getHeader("authorization");
            if(authHeader != null && authHeader.startsWith("Bearer ")){
                String token = authHeader.substring(7, 42);
                String username = authHeader.substring(43);
                String authToken = "dummyTokenForRateLimiterApplication";
                if(token.equals(authToken)){
                    Collection<GrantedAuthority> authorities = new ArrayList<>();
                    GrantedAuthority authority = new SimpleGrantedAuthority("ROLE_NORMAL_USER");
                    authorities.add(authority);
                    UsernamePasswordAuthenticationToken authenticationToken =
                            new UsernamePasswordAuthenticationToken(username, null, authorities);
                    SecurityContextHolder.getContext().setAuthentication(authenticationToken);
                }
            }
        }catch (Exception e){
            throw new RuntimeException("Failed to pass the authentication layer");
        }
        filterChain.doFilter(request, response);
    }
}
