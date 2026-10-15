package com.notes.sharing.service;

import com.notes.sharing.dto.DashboardResponse;
import com.notes.sharing.repository.FriendshipRepository;
import com.notes.sharing.repository.NoteRepository;
import com.notes.sharing.repository.NoteSharingRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class DashboardService {

    private final NoteRepository noteRepository;
    private final NoteSharingRepository sharingRepository;
    private final FriendshipRepository friendshipRepository;

    @Transactional(readOnly = true)
    public DashboardResponse stats(Long userId) {
        long friends = friendshipRepository.findForUser(userId).stream()
                .filter(f -> "accepted".equalsIgnoreCase(f.getStatus()))
                .count();
        return DashboardResponse.builder()
                .myNotesCount(noteRepository.findByOwner_UserID(userId).size())
                .sharedWithMeCount(sharingRepository.findByUser_UserID(userId).size())
                .friendsCount(friends)
                .build();
    }
}
