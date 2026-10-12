package com.notes.sharing.service;

import com.notes.sharing.dto.AuthResponse;
import com.notes.sharing.dto.LoginRequest;
import com.notes.sharing.dto.RegisterRequest;
import com.notes.sharing.entity.User;
import com.notes.sharing.entity.UserSettings;
import com.notes.sharing.exception.NotFoundException;
import com.notes.sharing.repository.UserRepository;
import com.notes.sharing.repository.UserSettingsRepository;
import com.notes.sharing.security.JwtService;
import com.notes.sharing.security.UserPrincipal;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepository;
    private final UserSettingsRepository settingsRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;

    @Transactional
    public AuthResponse register(RegisterRequest req) {
        String username = req.getUsername().trim();
        String email = req.getEmail().trim().toLowerCase();
        if (userRepository.findByEmail(email).isPresent()) {
            throw new IllegalArgumentException("Email already registered");
        }
        if (userRepository.findByUsername(username).isPresent()) {
            throw new IllegalArgumentException("Username already taken");
        }

        User user = User.builder()
                .username(username)
                .email(email)
                .passwordHash(passwordEncoder.encode(req.getPassword()))
                .build();

        User saved = userRepository.save(user);
        settingsRepository.save(UserSettings.builder().user(saved).build());
        String token = jwtService.generateToken(saved.getUserID(), saved.getEmail());
        return new AuthResponse(token, saved.getUserID(), saved.getUsername());
    }

    public AuthResponse login(LoginRequest req) {
        // Spring itself checks email + BCrypt password, I only ask it.
        Authentication auth;
        try {
            auth = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                    req.getEmail().trim().toLowerCase(), req.getPassword()));
        } catch (Exception e) {
            throw new NotFoundException("Invalid email or password");
        }
        UserPrincipal principal = (UserPrincipal) auth.getPrincipal();
        String token = jwtService.generateToken(principal.getUserId(), principal.getEmail());
        User user = userRepository.findById(principal.getUserId())
                .orElseThrow(() -> new NotFoundException("Invalid email or password"));
        return new AuthResponse(token, user.getUserID(), user.getUsername());
    }

    @Transactional
    public void changePassword(Long userId, String oldPassword, String newPassword) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new NotFoundException("User not found"));

        if (!passwordEncoder.matches(oldPassword, user.getPasswordHash())) {
            throw new IllegalArgumentException("Old password is wrong");
        }
        user.setPasswordHash(passwordEncoder.encode(newPassword));
        userRepository.save(user);
    }
}
