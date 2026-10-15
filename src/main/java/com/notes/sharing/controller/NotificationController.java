package com.notes.sharing.controller;

import com.notes.sharing.dto.NotificationResponse;
import com.notes.sharing.service.NotificationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import com.notes.sharing.security.CurrentUserId;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/notifications")
@RequiredArgsConstructor
public class NotificationController {

    private final NotificationService notificationService;

    @GetMapping
    public ResponseEntity<List<NotificationResponse>> mine(@CurrentUserId Long userId) {
        return ResponseEntity.ok(notificationService.myNotifications(userId));
    }

    @PutMapping("/{id}/read")
    public ResponseEntity<Void> markRead(@PathVariable Long id, @CurrentUserId Long userId) {
        notificationService.markRead(id, userId);
        return ResponseEntity.ok().build();
    }
}
