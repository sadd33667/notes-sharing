package com.notes.sharing.controller;

import com.notes.sharing.dto.FriendRequest;
import com.notes.sharing.dto.FriendshipResponse;
import com.notes.sharing.service.FriendshipService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import com.notes.sharing.security.CurrentUserId;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/friends")
@RequiredArgsConstructor
public class FriendshipController {

    private final FriendshipService friendshipService;

    @PostMapping("/request")
    public ResponseEntity<FriendshipResponse> sendRequest(
            @Valid @RequestBody FriendRequest body,
            @CurrentUserId Long userId) {
        FriendshipResponse res = friendshipService.sendRequest(userId, body.getUsername());
        return ResponseEntity.status(HttpStatus.CREATED)
                .header("Location", "/api/friends/" + res.getRelationshipID())
                .body(res);
    }

    @GetMapping
    public ResponseEntity<List<FriendshipResponse>> myFriends(@CurrentUserId Long userId) {
        return ResponseEntity.ok(friendshipService.myFriends(userId));
    }

    @GetMapping("/pending")
    public ResponseEntity<List<FriendshipResponse>> pending(@CurrentUserId Long userId) {
        return ResponseEntity.ok(friendshipService.pendingReceived(userId));
    }

    @PutMapping("/{id}/accept")
    public ResponseEntity<Void> accept(@PathVariable Long id, @CurrentUserId Long userId) {
        friendshipService.respond(id, userId, true);
        return ResponseEntity.ok().build();
    }

    @PutMapping("/{id}/reject")
    public ResponseEntity<Void> reject(@PathVariable Long id, @CurrentUserId Long userId) {
        friendshipService.respond(id, userId, false);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> remove(@PathVariable Long id, @CurrentUserId Long userId) {
        friendshipService.remove(id, userId);
        return ResponseEntity.noContent().build();
    }
}
