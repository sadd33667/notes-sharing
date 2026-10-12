package com.notes.sharing.controller;

import com.notes.sharing.dto.NoteResponse;
import com.notes.sharing.dto.ShareRequest;
import com.notes.sharing.service.SharingService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import com.notes.sharing.security.CurrentUserId;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class SharingController {

    private final SharingService sharingService;

    @PostMapping("/api/notes/{id}/share")
    public ResponseEntity<Void> share(
            @PathVariable Long id,
            @Valid @RequestBody ShareRequest req,
            @CurrentUserId Long userId) {
        sharingService.share(id, userId, req.getUserId(), req.getPermission());
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/api/notes/{id}/share/{userId}")
    public ResponseEntity<Void> unshare(
            @PathVariable Long id,
            @PathVariable Long userId,
            @CurrentUserId Long requesterId) {
        sharingService.unshare(id, requesterId, userId);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/api/notes/shared-with-me")
    public ResponseEntity<List<NoteResponse>> sharedWithMe(@CurrentUserId Long userId) {
        return ResponseEntity.ok(sharingService.sharedWithMe(userId));
    }

    @PutMapping("/api/notes/{id}/favorite")
    public ResponseEntity<Void> setFavorite(
            @PathVariable Long id,
            @RequestBody java.util.Map<String, Boolean> body,
            @CurrentUserId Long userId) {
        sharingService.setFavorite(id, userId,
                body.getOrDefault("isFavorite", true));
        return ResponseEntity.ok().build();
    }
}
