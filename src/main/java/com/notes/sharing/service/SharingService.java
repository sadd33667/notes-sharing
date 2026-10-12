package com.notes.sharing.service;

import com.notes.sharing.dto.NoteResponse;
import com.notes.sharing.entity.Note;
import com.notes.sharing.entity.NoteSharing;
import com.notes.sharing.entity.Notification;
import com.notes.sharing.entity.User;
import com.notes.sharing.mapper.NoteMapper;
import com.notes.sharing.exception.ForbiddenException;
import com.notes.sharing.exception.ConflictException;
import com.notes.sharing.exception.NotFoundException;
import com.notes.sharing.repository.NoteRepository;
import com.notes.sharing.repository.NoteSharingRepository;
import com.notes.sharing.repository.NotificationRepository;
import com.notes.sharing.repository.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

@Service
public class SharingService {
    private final NoteRepository noteRepository;
    private final UserRepository userRepository;
    private final NoteSharingRepository sharingRepository;
    private final NotificationRepository notificationRepository;
    private final NoteMapper noteMapper;

    @Transactional
    public void share(Long noteId, Long ownerId, Long targetUserId, String permission) {
        Note note = noteRepository.findById(noteId).orElseThrow(() -> new NotFoundException(1002, "Note not found: " + noteId));
        if (!note.getOwner().getUserID().equals(ownerId)) {
            throw new ForbiddenException(2005, "Only the owner can share this note");
        }
        User target = userRepository.findById(targetUserId).orElseThrow(() -> new NotFoundException(1001, "User not found: " + targetUserId));
        if (target.getUserID().equals(ownerId)) {
            throw new ConflictException(3005, "Cannot share with yourself");
        }
        NoteSharing sharing = sharingRepository.findByNote_NoteID(noteId).stream().filter(s -> s.getUser().getUserID().equals(targetUserId)).findFirst().orElseGet(() -> NoteSharing.builder().note(note).user(target).build());
        sharing.setPermission(permission != null ? permission.toLowerCase() : "view");
        if (!"view".equals(sharing.getPermission()) && !"edit".equals(sharing.getPermission())) {
            throw new ConflictException(3003, "Permission must be view or edit");
        }
        sharingRepository.save(sharing);
        notificationRepository.save(Notification.builder().notifier(note.getOwner()).notified(target).type("share").note(note).build());
    }

    @Transactional
    public void unshare(Long noteId, Long ownerId, Long targetUserId) {
        Note note = noteRepository.findById(noteId).orElseThrow(() -> new NotFoundException(1002, "Note not found: " + noteId));
        if (!note.getOwner().getUserID().equals(ownerId)) {
            throw new ForbiddenException(2005, "Only the owner can unshare this note");
        }
        sharingRepository.findByNote_NoteID(noteId).stream().filter(s -> s.getUser().getUserID().equals(targetUserId)).findFirst().ifPresent(sharingRepository::delete);
    }

    public List<NoteResponse> sharedWithMe(Long userId) {
        return sharingRepository.findByUser_UserID(userId).stream().map(s -> noteMapper.toResponse(s.getNote())).toList();
    }

    @Transactional
    public void setFavorite(Long noteId, Long userId, boolean favorite) {
        NoteSharing sharing = sharingRepository.findByNote_NoteID(noteId).stream().filter(s -> s.getUser().getUserID().equals(userId)).findFirst().orElseThrow(() -> new NotFoundException(1002, "Note not found: " + noteId));
        sharing.setIsFavorite(favorite);
        sharingRepository.save(sharing);
    }
    public SharingService(final NoteRepository noteRepository, final UserRepository userRepository, final NoteSharingRepository sharingRepository, final NotificationRepository notificationRepository, final NoteMapper noteMapper) {
        this.noteRepository = noteRepository;
        this.userRepository = userRepository;
        this.sharingRepository = sharingRepository;
        this.notificationRepository = notificationRepository;
        this.noteMapper = noteMapper;
    }
}
