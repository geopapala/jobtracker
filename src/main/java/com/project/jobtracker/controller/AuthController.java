package com.project.jobtracker.controller;

import com.project.jobtracker.dto.LoginRequest;
import com.project.jobtracker.dto.LoginResponse;
import com.project.jobtracker.dto.UserRegisterRequest;
import com.project.jobtracker.dto.UserResponse;
import com.project.jobtracker.service.AuthService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/register")
    public ResponseEntity<UserResponse> register(@RequestBody UserRegisterRequest request) {
        return ResponseEntity.ok(authService.register(request));
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(@RequestBody LoginRequest request) {
        return ResponseEntity.ok(authService.login(request));
    }
}
