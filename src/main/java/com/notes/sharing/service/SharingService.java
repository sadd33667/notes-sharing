package com.notes.sharing.service;

import com.notes.sharing.dto.NoteResponse;
import com.notes.sharing.entity.Note;
import com.notes.sharing.entity.NoteSharing;
import com.notes.sharing.entity.User;
import com.notes.sharing.exception.NotFoundException;
import com.notes.sharing.repository.NoteRepository;
import com.notes.sharing.repository.NoteSharingRepository;
import com.notes.sharing.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class SharingService {

    private final NoteRepository noteRepository;
    private final UserRepository userRepository;
    private final NoteSharingRepository sharingRepository;

    @Transactional
    public void share(Long noteId, Long ownerId, Long targetUserId, String permission) {
        Note note = noteRepository.findById(noteId)
                .orElseThrow(() -> new NotFoundException("Note not found: " + noteId));

        if (!note.getOwner().getUserID().equals(ownerId)) {
            throw new NotFoundException("Note not found: " + noteId);
        }
        User target = userRepository.findById(targetUserId)
                .orElseThrow(() -> new NotFoundException("User not found: " + targetUserId));

        NoteSharing sharing = sharingRepository.findByNote_NoteID(noteId).stream()
                .filter(s -> s.getUser().getUserID().equals(targetUserId))
                .findFirst()
                .orElseGet(() -> NoteSharing.builder().note(note).user(target).build());

        sharing.setPermission(permission != null ? permission : "view");
        sharingRepository.save(sharing);
    }

    @Transactional
    public void unshare(Long noteId, Long ownerId, Long targetUserId) {
        Note note = noteRepository.findById(noteId)
                .orElseThrow(() -> new NotFoundException("Note not found: " + noteId));

        if (!note.getOwner().getUserID().equals(ownerId)) {
            throw new NotFoundException("Note not found: " + noteId);
        }
        sharingRepository.findByNote_NoteID(noteId).stream()
                .filter(s -> s.getUser().getUserID().equals(targetUserId))
                .findFirst()
                .ifPresent(sharingRepository::delete);
    }

    public List<NoteResponse> sharedWithMe(Long userId) {
        return sharingRepository.findByUser_UserID(userId).stream()
                .map(s -> NoteService.toResponse(s.getNote()))
                .toList();
    }
}
