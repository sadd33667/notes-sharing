package com.notes.sharing.controller;

import com.notes.sharing.dto.FriendshipResponse;
import com.notes.sharing.service.FriendshipService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/friends")
@RequiredArgsConstructor
public class FriendshipController {

    private final FriendshipService friendshipService;

    @PostMapping("/request")
    public ResponseEntity<FriendshipResponse> sendRequest(
            @RequestBody Map<String, String> body,
            Authentication auth) {
        return ResponseEntity.status(HttpStatus.CREATED).body(
                friendshipService.sendRequest((Long) auth.getPrincipal(), body.get("username")));
    }

    @GetMapping
    public ResponseEntity<List<FriendshipResponse>> myFriends(Authentication auth) {
        return ResponseEntity.ok(friendshipService.myFriends((Long) auth.getPrincipal()));
    }

    @GetMapping("/pending")
    public ResponseEntity<List<FriendshipResponse>> pending(Authentication auth) {
        return ResponseEntity.ok(friendshipService.pendingReceived((Long) auth.getPrincipal()));
    }

    @PutMapping("/{id}/accept")
    public ResponseEntity<Void> accept(@PathVariable Long id, Authentication auth) {
        friendshipService.respond(id, (Long) auth.getPrincipal(), true);
        return ResponseEntity.ok().build();
    }

    @PutMapping("/{id}/reject")
    public ResponseEntity<Void> reject(@PathVariable Long id, Authentication auth) {
        friendshipService.respond(id, (Long) auth.getPrincipal(), false);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> remove(@PathVariable Long id, Authentication auth) {
        friendshipService.remove(id, (Long) auth.getPrincipal());
        return ResponseEntity.noContent().build();
    }
}
