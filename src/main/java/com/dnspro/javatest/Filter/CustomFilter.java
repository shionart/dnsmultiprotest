package com.dnspro.javatest.Filter;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.dnspro.javatest.Services.CustomUserDetailsService;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class CustomFilter extends OncePerRequestFilter{

    private final JWTService jwtService;
    private final CustomUserDetailsService userDetailsService;

    @Override
    protected void doFilterInternal(
        @NonNull HttpServletRequest request, 
        @NonNull HttpServletResponse response, 
        @NonNull FilterChain filterChain)
        throws ServletException, IOException {
            System.out.println("Custom Filter JWT started...");
            final String authHeader = request.getHeader("Authorization");
            final String jwtToken;
            final String username;
            if (authHeader== null || !authHeader.startsWith("Bearer") ) { //tanpa token
                System.out.println("No Bearer authentication header");
                filterChain.doFilter(request, response);
                return;
            }
            jwtToken = authHeader.substring(7);
            System.out.println("Jwt token: " + jwtToken); //TODO delete pindah env
            username = jwtService.extractUsername(jwtToken); //Baca token  
            if (username!=null && SecurityContextHolder.getContext().getAuthentication()==null) { 
                System.out.println("Username filled & not authenticated, checking Token...");
                UserDetails userDetails = userDetailsService.loadUserByUsername(username);
                if (jwtService.isTokenValid(jwtToken, userDetails)) {
                    System.out.println("Token & Username valid , authenticating...");
                    UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
                        userDetails, 
                        null,
                        userDetails.getAuthorities());
                    authToken.setDetails(
                        new WebAuthenticationDetailsSource().buildDetails(request)
                    );
                    SecurityContextHolder.getContext().setAuthentication(authToken);
                }
            }  
            System.out.println("Custom Filter JWT is done, continuing...");
            filterChain.doFilter(request, response);
    }

    
}
