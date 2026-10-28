package com.notes.sharing.controller;

import com.notes.sharing.dto.UserSummary;
import com.notes.sharing.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserSearchController {

    private final UserService userService;

    @GetMapping("/search")
    public ResponseEntity<List<UserSummary>> search(@RequestParam String username) {
        return ResponseEntity.ok(userService.searchUsers(username));
    }
}