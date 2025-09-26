package com.project.jobtracker.service;

import com.project.jobtracker.dto.UserRegisterRequest;
import com.project.jobtracker.dto.UserResponse;
import com.project.jobtracker.model.User;
import com.project.jobtracker.repository.UserRepository;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    private final UserRepository userRepository;
    private final BCryptPasswordEncoder passwordEncoder;

    public AuthService(UserRepository userRepository) {
        this.userRepository = userRepository;
        this.passwordEncoder = new BCryptPasswordEncoder();
    }

    public UserResponse register(UserRegisterRequest request) {
        if (userRepository.existsByEmail(request.getEmail())) {
            throw new IllegalArgumentException("Email already in use");
        }

        String hashedPassword = passwordEncoder.encode(request.getPassword());

        User user = new User (
                request.getName(),
                request.getEmail(),
                hashedPassword
        );

        User savedUser = userRepository.save(user);

        return new UserResponse(
                savedUser.getId().toString(),
                savedUser.getName(),
                savedUser.getEmail(),
                savedUser.getCreatedAt().toString()
        );
    }
}
