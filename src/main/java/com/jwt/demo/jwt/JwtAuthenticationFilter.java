package com.jwt.demo.jwt;


import io.jsonwebtoken.ExpiredJwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;


@Component
public class JwtAuthenticationFilter  extends OncePerRequestFilter {

    private final JwtService jwtService;
    private final UserDetailsService userDetailsService;

    public JwtAuthenticationFilter(JwtService jwtService, UserDetailsService userDetailsService) {
        this.jwtService = jwtService;
        this.userDetailsService = userDetailsService;
    }


    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {


        String header;
        String token;
        String username;

         header = request.getHeader("Authorization");

        if(header ==null){
            filterChain.doFilter(request,response);
            return;
        }
        token = header.substring(7);
        try{
            username = jwtService.getUserNameFromToken(token);
            if(username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
                UserDetails userDetails= userDetailsService.loadUserByUsername(username);
                if(!jwtService.isTokenExpired(token) && userDetails!=null) {
                    UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
                    SecurityContextHolder.getContext().setAuthentication(authentication);

                }

            }
        }
        catch (ExpiredJwtException expiredJwtException){
            System.out.println("Token expired: " + expiredJwtException.getMessage());
        }
        catch (Exception e){
            System.out.println("Error in doFilterInternal: " + e.getMessage());
        }

        filterChain.doFilter(request,response);
    }
}
