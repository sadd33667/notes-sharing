package com.notes.sharing.service;

import com.notes.sharing.dto.AuthResponse;
import com.notes.sharing.dto.LoginRequest;
import com.notes.sharing.dto.RegisterRequest;
import com.notes.sharing.entity.User;
import com.notes.sharing.exception.NotFoundException;
import com.notes.sharing.repository.UserRepository;
import com.notes.sharing.security.JwtService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;

    @Transactional
    public AuthResponse register(RegisterRequest req) {
        if (userRepository.findByEmail(req.getEmail()).isPresent()) {
            throw new IllegalArgumentException("Email already registered");
        }

        User user = User.builder()
                .username(req.getUsername())
                .email(req.getEmail())
                .passwordHash(passwordEncoder.encode(req.getPassword()))
                .build();

        User saved = userRepository.save(user);
        String token = jwtService.generateToken(saved.getUserID(), saved.getEmail());
        return new AuthResponse(token, saved.getUserID(), saved.getUsername());
    }

    public AuthResponse login(LoginRequest req) {
        User user = userRepository.findByEmail(req.getEmail())
                .orElseThrow(() -> new NotFoundException("Invalid email or password"));

        if (!passwordEncoder.matches(req.getPassword(), user.getPasswordHash())) {
            throw new NotFoundException("Invalid email or password");
        }

        String token = jwtService.generateToken(user.getUserID(), user.getEmail());
        return new AuthResponse(token, user.getUserID(), user.getUsername());
    }
}
