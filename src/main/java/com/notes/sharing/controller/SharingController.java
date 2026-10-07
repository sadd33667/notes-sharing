package com.notes.sharing.controller;

import com.notes.sharing.dto.NoteResponse;
import com.notes.sharing.dto.ShareRequest;
import com.notes.sharing.service.SharingService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
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
            Authentication auth) {
        sharingService.share(id, (Long) auth.getPrincipal(), req.getUserId(), req.getPermission());
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/api/notes/{id}/share/{userId}")
    public ResponseEntity<Void> unshare(
            @PathVariable Long id,
            @PathVariable Long userId,
            Authentication auth) {
        sharingService.unshare(id, (Long) auth.getPrincipal(), userId);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/api/notes/shared-with-me")
    public ResponseEntity<List<NoteResponse>> sharedWithMe(Authentication auth) {
        return ResponseEntity.ok(sharingService.sharedWithMe((Long) auth.getPrincipal()));
    }

    @PutMapping("/api/notes/{id}/favorite")
    public ResponseEntity<Void> setFavorite(
            @PathVariable Long id,
            @RequestBody java.util.Map<String, Boolean> body,
            Authentication auth) {
        sharingService.setFavorite(id, (Long) auth.getPrincipal(),
                body.getOrDefault("isFavorite", true));
        return ResponseEntity.ok().build();
    }
}
