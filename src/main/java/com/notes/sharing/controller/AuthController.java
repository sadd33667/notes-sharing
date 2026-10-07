package com.notes.sharing.controller;

import com.notes.sharing.dto.AuthResponse;
import com.notes.sharing.dto.ChangePasswordRequest;
import com.notes.sharing.dto.LoginRequest;
import com.notes.sharing.dto.RegisterRequest;
import com.notes.sharing.service.AuthService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping("/register")
    public ResponseEntity<AuthResponse> register(@Valid @RequestBody RegisterRequest req) {
        return ResponseEntity.status(HttpStatus.CREATED).body(authService.register(req));
    }

    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(@Valid @RequestBody LoginRequest req) {
        return ResponseEntity.ok(authService.login(req));
    }

    @PutMapping("/password")
    public ResponseEntity<Void> changePassword(
            @Valid @RequestBody ChangePasswordRequest body,
            org.springframework.security.core.Authentication auth) {
        authService.changePassword((Long) auth.getPrincipal(), body.getOldPassword(), body.getNewPassword());
        return ResponseEntity.ok().build();
    }
}
