package com.project.jobtracker.service;

import com.project.jobtracker.dto.LoginRequest;
import com.project.jobtracker.dto.LoginResponse;
import com.project.jobtracker.dto.UserRegisterRequest;
import com.project.jobtracker.dto.UserResponse;
import com.project.jobtracker.model.User;
import com.project.jobtracker.repository.UserRepository;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
public class AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;

    public AuthService(UserRepository userRepository,
                       PasswordEncoder passwordEncoder,
                       AuthenticationManager authenticationManager,
                       JwtService jwtService) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.authenticationManager = authenticationManager;
        this.jwtService = jwtService;
    }

    public UserResponse register(UserRegisterRequest request) {
        if (userRepository.existsByEmail(request.email())) {
            throw new RuntimeException("Email already in use");
        }
        User user = new User(
                request.name(),
                request.email(),
                passwordEncoder.encode(request.password())
        );
        User savedUser = userRepository.save(user);
        return new UserResponse(
                savedUser.getId().toString(),
                savedUser.getName(),
                savedUser.getEmail(),
                savedUser.getCreatedAt().toString()
        );
    }

    public LoginResponse login(LoginRequest request) {
        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(request.email(), request.password())
            );
        } catch (AuthenticationException e) {
            // catch *all* Spring Security auth exceptions
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Invalid email or password");
        }

        User user = userRepository.findByEmail(request.email())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Invalid email or password"));

        String token = jwtService.generateToken(user.getEmail());
        return new LoginResponse(token);
    }

}
