package com.notes.sharing.controller;

import com.notes.sharing.dto.ProfileResponse;
import com.notes.sharing.dto.SettingsResponse;
import com.notes.sharing.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/users/me")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @GetMapping
    public ResponseEntity<ProfileResponse> getProfile(Authentication auth) {
        return ResponseEntity.ok(userService.getProfile((Long) auth.getPrincipal()));
    }

    @PutMapping
    public ResponseEntity<ProfileResponse> updateProfile(
            @RequestBody Map<String, String> body, Authentication auth) {
        return ResponseEntity.ok(userService.updateProfile((Long) auth.getPrincipal(), body));
    }

    @GetMapping("/settings")
    public ResponseEntity<SettingsResponse> getSettings(Authentication auth) {
        return ResponseEntity.ok(userService.getSettings((Long) auth.getPrincipal()));
    }

    @PutMapping("/settings")
    public ResponseEntity<SettingsResponse> updateSettings(
            @RequestBody Map<String, Object> body, Authentication auth) {
        return ResponseEntity.ok(userService.updateSettings((Long) auth.getPrincipal(), body));
    }

    @DeleteMapping
    public ResponseEntity<Void> deleteAccount(Authentication auth) {
        userService.deleteAccount((Long) auth.getPrincipal());
        return ResponseEntity.noContent().build();
    }
}
