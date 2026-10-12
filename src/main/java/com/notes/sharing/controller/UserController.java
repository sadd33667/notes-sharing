package com.notes.sharing.controller;

import com.notes.sharing.dto.ProfileResponse;
import com.notes.sharing.dto.SettingsResponse;
import com.notes.sharing.dto.UpdateProfileRequest;
import com.notes.sharing.dto.UpdateSettingsRequest;
import com.notes.sharing.service.UserService;
import org.springframework.http.ResponseEntity;
import com.notes.sharing.security.CurrentUserId;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/users/me")
public class UserController {
    private final UserService userService;

    @GetMapping
    public ResponseEntity<ProfileResponse> getProfile(@CurrentUserId Long userId) {
        return ResponseEntity.ok(userService.getProfile(userId));
    }

    @PutMapping
    public ResponseEntity<ProfileResponse> updateProfile(@RequestBody UpdateProfileRequest body, @CurrentUserId Long userId) {
        return ResponseEntity.ok(userService.updateProfile(userId, body));
    }

    @GetMapping("/settings")
    public ResponseEntity<SettingsResponse> getSettings(@CurrentUserId Long userId) {
        return ResponseEntity.ok(userService.getSettings(userId));
    }

    @PutMapping("/settings")
    public ResponseEntity<SettingsResponse> updateSettings(@RequestBody UpdateSettingsRequest body, @CurrentUserId Long userId) {
        return ResponseEntity.ok(userService.updateSettings(userId, body));
    }

    @DeleteMapping
    public ResponseEntity<Void> deleteAccount(@CurrentUserId Long userId) {
        userService.deleteAccount(userId);
        return ResponseEntity.noContent().build();
    }
    public UserController(final UserService userService) {
        this.userService = userService;
    }
}
