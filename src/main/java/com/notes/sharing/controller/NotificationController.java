package com.notes.sharing.controller;

import com.notes.sharing.entity.Notification;
import com.notes.sharing.service.NotificationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/notifications")
@RequiredArgsConstructor
public class NotificationController {

    private final NotificationService notificationService;

    @GetMapping
    public ResponseEntity<List<Notification>> mine(Authentication auth) {
        return ResponseEntity.ok(notificationService.myNotifications((Long) auth.getPrincipal()));
    }

    @PutMapping("/{id}/read")
    public ResponseEntity<Void> markRead(@PathVariable Long id, Authentication auth) {
        notificationService.markRead(id, (Long) auth.getPrincipal());
        return ResponseEntity.ok().build();
    }
}
