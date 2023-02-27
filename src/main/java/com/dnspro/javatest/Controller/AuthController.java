package com.dnspro.javatest.Controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.dnspro.javatest.DTO.AuthResponse;
import com.dnspro.javatest.DTO.DTOUser;
import com.dnspro.javatest.Services.AuthServices;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthServices authService;

    @PostMapping("/register")
    public ResponseEntity<AuthResponse> register(@RequestBody DTOUser dtoUser) {
        return ResponseEntity.ok(authService.register(dtoUser));
    }
    @PostMapping("/authenticate")
    public ResponseEntity<AuthResponse> authenticate(@RequestBody DTOUser dtoUser) {
        return ResponseEntity.ok(authService.authenticate(dtoUser));
    }
}
