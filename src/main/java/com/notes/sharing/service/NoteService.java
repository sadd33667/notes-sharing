package com.notes.sharing.service;

import com.notes.sharing.dto.CreateNoteRequest;
import com.notes.sharing.dto.NoteResponse;
import com.notes.sharing.entity.Note;
import com.notes.sharing.entity.User;
import com.notes.sharing.exception.NotFoundException;
import com.notes.sharing.repository.NoteRepository;
import com.notes.sharing.repository.NoteSharingRepository;
import com.notes.sharing.repository.NotificationRepository;
import com.notes.sharing.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class NoteService {

    private final NoteRepository noteRepository;
    private final UserRepository userRepository;
    private final NoteSharingRepository sharingRepository;
    private final NotificationRepository notificationRepository;

    @Transactional
    public NoteResponse createNote(CreateNoteRequest req) {
        User owner = userRepository.findById(req.getOwnerId())
                .orElseThrow(() -> new NotFoundException("User not found: " + req.getOwnerId()));

        Note note = Note.builder()
                .owner(owner)
                .title(req.getTitle())
                .contents(req.getContents())
                .noteType(req.getNoteType() != null ? req.getNoteType() : "text")
                .color(req.getColor())
                .visibility(req.getVisibility() != null ? req.getVisibility() : "private")
                .build();

        if (!"private".equalsIgnoreCase(note.getVisibility())
                && !"public".equalsIgnoreCase(note.getVisibility())
                && !"shared".equalsIgnoreCase(note.getVisibility())) {
            throw new IllegalArgumentException("Visibility must be private, public or shared");
        }
        note.setVisibility(note.getVisibility().toLowerCase());

        Note saved = noteRepository.save(note);
        return toResponse(saved);
    }

    @Transactional(readOnly = true)
    public NoteResponse getNote(Long noteId, Long requesterId) {        Note note = noteRepository.findById(noteId)
                .orElseThrow(() -> new NotFoundException("Note not found: " + noteId));

        boolean isOwner = note.getOwner().getUserID().equals(requesterId);
        boolean isShared = sharingRepository.findByNote_NoteID(noteId).stream()
                .anyMatch(s -> s.getUser().getUserID().equals(requesterId));
        boolean isPublic = "public".equalsIgnoreCase(note.getVisibility());

        if (!isOwner && !isShared && !isPublic) {
            throw new NotFoundException("Note not found: " + noteId);
        }
        return toResponse(note);
    }

    @Transactional
    public NoteResponse updateNote(Long noteId, Long requesterId, String title, String contents) {
        Note note = noteRepository.findById(noteId)
                .orElseThrow(() -> new NotFoundException("Note not found: " + noteId));

        boolean isOwner = note.getOwner().getUserID().equals(requesterId);
        boolean canEdit = sharingRepository.findByNote_NoteID(noteId).stream()
                .anyMatch(s -> s.getUser().getUserID().equals(requesterId)
                        && "edit".equalsIgnoreCase(s.getPermission()));

        if (!isOwner && !canEdit) {
            throw new NotFoundException("Note not found: " + noteId);
        }
        if (title != null && !title.isBlank()) note.setTitle(title);
        if (contents != null) note.setContents(contents);
        return toResponse(noteRepository.save(note));
    }

    @Transactional
    public void deleteNote(Long noteId, Long requesterId) {
        Note note = noteRepository.findById(noteId)
                .orElseThrow(() -> new NotFoundException("Note not found: " + noteId));

        if (!note.getOwner().getUserID().equals(requesterId)) {
            throw new NotFoundException("Note not found: " + noteId);
        }
        notificationRepository.deleteByNote(noteId);
        sharingRepository.deleteByNote(noteId);
        noteRepository.delete(note);
    }

    @Transactional(readOnly = true)
    public List<NoteResponse> myNotes(Long ownerId) {
        return noteRepository.findMineFiltered(ownerId, null).stream()
                .map(NoteService::toResponse)
                .toList();
    }

    public List<NoteResponse> myNotesByType(Long ownerId, String type) {
        return noteRepository.findMineFiltered(ownerId, type).stream()
                .map(NoteService::toResponse)
                .toList();
    }

    @Transactional(readOnly = true)
    public List<NoteResponse> upcoming(Long ownerId, LocalDateTime from, LocalDateTime to) {
        if (from.isAfter(to)) {
            throw new IllegalArgumentException("From date must be before to date");
        }
        return noteRepository.findUpcoming(ownerId, from, to).stream()
                .map(NoteService::toResponse)
                .toList();
    }

    @Transactional
    public NoteResponse setPinned(Long noteId, Long requesterId, boolean pinned) {
        Note note = noteRepository.findById(noteId)
                .orElseThrow(() -> new NotFoundException("Note not found: " + noteId));

        if (!note.getOwner().getUserID().equals(requesterId)) {
            throw new NotFoundException("Note not found: " + noteId);
        }
        note.setIsPinned(pinned);
        return toResponse(noteRepository.save(note));
    }

    public static NoteResponse toResponse(Note n) {
        return NoteResponse.builder()
                .noteID(n.getNoteID())
                .ownerId(n.getOwner().getUserID())
                .title(n.getTitle())
                .contents(n.getContents())
                .noteType(n.getNoteType())
                .color(n.getColor())
                .isPinned(n.getIsPinned())
                .createdAt(n.getCreatedAt())
                .visibility(n.getVisibility())
                .build();
    }
}
