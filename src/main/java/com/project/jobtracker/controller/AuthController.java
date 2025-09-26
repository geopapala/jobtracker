package com.project.jobtracker.controller;

import com.project.jobtracker.dto.UserRegisterRequest;
import com.project.jobtracker.dto.UserResponse;
import com.project.jobtracker.service.AuthService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/register")
    public ResponseEntity<UserResponse> register(@RequestBody UserRegisterRequest request) {
        UserResponse userResponse = authService.register(request);
        return ResponseEntity.status(201).body(userResponse);
    }
}
