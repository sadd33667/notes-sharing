package com.notes.sharing.service;

import com.notes.sharing.dto.NoteResponse;
import com.notes.sharing.entity.Note;
import com.notes.sharing.entity.NoteSharing;
import com.notes.sharing.entity.Notification;
import com.notes.sharing.entity.User;
import com.notes.sharing.mapper.NoteMapper;
import com.notes.sharing.exception.ErrorCode;
import com.notes.sharing.exception.SystemException;
import com.notes.sharing.repository.NoteRepository;
import com.notes.sharing.repository.NoteSharingRepository;
import com.notes.sharing.repository.NotificationRepository;
import com.notes.sharing.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class SharingService {

    private final NoteRepository noteRepository;
    private final UserRepository userRepository;
    private final NoteSharingRepository sharingRepository;
    private final NotificationRepository notificationRepository;
    private final NoteMapper noteMapper;
    private final Logger log = LoggerFactory.getLogger(SharingService.class);

    @Transactional
    public void share(Long noteId, Long ownerId, Long targetUserId, String permission) {
        Note note = noteRepository.findById(noteId)
                .orElseThrow(() -> new SystemException(ErrorCode.NOTE_NOT_FOUND, "Note not found: " + noteId));

        if (!note.getOwner().getUserID().equals(ownerId)) {
            throw new SystemException(ErrorCode.NOTE_SHARE_DENIED, "Only the owner can share this note");
        }
        User target = userRepository.findById(targetUserId)
                .orElseThrow(() -> new SystemException(ErrorCode.USER_NOT_FOUND, "User not found: " + targetUserId));

        if (target.getUserID().equals(ownerId)) {
            throw new SystemException(ErrorCode.SELF_ACTION, "Cannot share with yourself");
        }

        NoteSharing sharing = sharingRepository.findByNote_NoteID(noteId).stream()
                .filter(s -> s.getUser().getUserID().equals(targetUserId))
                .findFirst()
                .orElseGet(() -> NoteSharing.builder().note(note).user(target).build());

        sharing.setPermission(permission != null ? permission.toLowerCase() : "view");
        if (!"view".equals(sharing.getPermission()) && !"edit".equals(sharing.getPermission())) {
            throw new SystemException(ErrorCode.BAD_PERMISSION, "Permission must be view or edit");
        }
        sharingRepository.save(sharing);
        notificationRepository.save(Notification.builder()
                .notifier(note.getOwner())
                .notified(target)
                .type("share")
                .note(note)
                .build());
        log.info("Note {} shared by user {} with user {} (permission={})", noteId, ownerId, targetUserId, sharing.getPermission());
    }

    @Transactional
    public void unshare(Long noteId, Long ownerId, Long targetUserId) {
        Note note = noteRepository.findById(noteId)
                .orElseThrow(() -> new SystemException(ErrorCode.NOTE_NOT_FOUND, "Note not found: " + noteId));

        if (!note.getOwner().getUserID().equals(ownerId)) {
            throw new SystemException(ErrorCode.NOTE_SHARE_DENIED, "Only the owner can unshare this note");
        }
        sharingRepository.findByNote_NoteID(noteId).stream()
                .filter(s -> s.getUser().getUserID().equals(targetUserId))
                .findFirst()
                .ifPresent(sharingRepository::delete);
        log.info("Note {} unshared by user {} with user {}", noteId, ownerId, targetUserId);
    }

    @Transactional(readOnly = true)
    public List<NoteResponse> sharedWithMe(Long userId) {
        return sharingRepository.findByUser_UserID(userId).stream()
                .map(s -> noteMapper.toResponse(s.getNote()))
                .toList();
    }

    @Transactional
    public void setFavorite(Long noteId, Long userId, boolean favorite) {
        NoteSharing sharing = sharingRepository.findByNote_NoteID(noteId).stream()
                .filter(s -> s.getUser().getUserID().equals(userId))
                .findFirst()
                .orElseThrow(() -> new SystemException(ErrorCode.NOTE_NOT_FOUND, "Note not found: " + noteId));
        sharing.setIsFavorite(favorite);
        sharingRepository.save(sharing);
        log.info("Note {} favorite={} by user {}", noteId, favorite, userId);
    }
}
