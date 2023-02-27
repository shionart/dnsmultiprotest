package com.dnspro.javatest.Services;

import java.util.HashMap;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.dnspro.javatest.DTO.AuthResponse;
import com.dnspro.javatest.DTO.DTOUser;
import com.dnspro.javatest.Filter.JWTService;
import com.dnspro.javatest.Repository.UserRepository;
import com.dnspro.javatest.User.Role;
import com.dnspro.javatest.User.User;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AuthServices {
    
    private final JWTService jwtService;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;

    public AuthResponse register(DTOUser dtoUser) {
        User user = User.builder()
                    .username(dtoUser.getUsername())
                    .password(passwordEncoder.encode(dtoUser.getPassword()))
                    .role(Role.USER)
                    .build();
        try {
            userRepository.save(user);
        } catch (Exception e) {
            throw e;
        }
        String tokenjwt = jwtService.generateJWTToken(new HashMap<>(), user);
        return AuthResponse.builder()
        .token(tokenjwt)
        .build();
    }

    public AuthResponse authenticate(DTOUser dtoUser){
     authenticationManager.authenticate(
            new UsernamePasswordAuthenticationToken(
                dtoUser.getUsername(), 
                dtoUser.getPassword())
        );
        User user = userRepository.findByUsername(dtoUser.getUsername()).orElseThrow();
        String tokenjwt = jwtService.generateJWTToken(new HashMap<>(), user);
        return AuthResponse.builder()
            .token(tokenjwt)
            .build();    
    }
}
