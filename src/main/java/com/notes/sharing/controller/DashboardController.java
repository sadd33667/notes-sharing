package com.notes.sharing.controller;

import com.notes.sharing.dto.DashboardResponse;
import com.notes.sharing.service.DashboardService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import com.notes.sharing.security.CurrentUserId;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/dashboard")
@RequiredArgsConstructor
public class DashboardController {

    private final DashboardService dashboardService;

    @GetMapping
    public ResponseEntity<DashboardResponse> stats(@CurrentUserId Long userId) {
        return ResponseEntity.ok(dashboardService.stats(userId));
    }
}
