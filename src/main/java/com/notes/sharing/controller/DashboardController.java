package com.notes.sharing.controller;

import com.notes.sharing.dto.DashboardResponse;
import com.notes.sharing.repository.FriendshipRepository;
import com.notes.sharing.repository.NoteRepository;
import com.notes.sharing.repository.NoteSharingRepository;
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

    private final NoteRepository noteRepository;
    private final NoteSharingRepository sharingRepository;
    private final FriendshipRepository friendshipRepository;

    @GetMapping
    public ResponseEntity<DashboardResponse> stats(Authentication auth) {
        Long userId = (Long) auth.getPrincipal();
        long friends = friendshipRepository.findForUser(userId).stream()
                .filter(f -> "accepted".equalsIgnoreCase(f.getStatus()))
                .count();
        return ResponseEntity.ok(DashboardResponse.builder()
                .myNotesCount(noteRepository.findByOwner_UserID(userId).size())
                .sharedWithMeCount(sharingRepository.findByUser_UserID(userId).size())
                .friendsCount(friends)
                .build());
    }
}
