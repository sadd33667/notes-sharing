package com.notes.sharing.controller;

import com.notes.sharing.dto.DashboardResponse;
import com.notes.sharing.service.DashboardService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/dashboard")
@RequiredArgsConstructor
public class DashboardController {

    private final DashboardService dashboardService;

    @GetMapping
    public ResponseEntity<DashboardResponse> stats(Authentication auth) {
        return ResponseEntity.ok(dashboardService.stats((Long) auth.getPrincipal()));
    }
}
