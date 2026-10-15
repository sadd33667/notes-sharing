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
import com.notes.sharing.security.CurrentUserId;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping("/register")
    public ResponseEntity<AuthResponse> register(@Valid @RequestBody RegisterRequest req) {
        AuthResponse res = authService.register(req);
        return ResponseEntity.status(HttpStatus.CREATED)
                .header("Location", "/api/users/" + res.getUserId())
                .body(res);
    }

    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(@Valid @RequestBody LoginRequest req) {
        return ResponseEntity.ok(authService.login(req));
    }

    @PutMapping("/password")
    public ResponseEntity<Void> changePassword(
            @Valid @RequestBody ChangePasswordRequest body,
            @CurrentUserId Long userId) {
        authService.changePassword(userId, body.getOldPassword(), body.getNewPassword());
        return ResponseEntity.ok().build();
    }
    
}
