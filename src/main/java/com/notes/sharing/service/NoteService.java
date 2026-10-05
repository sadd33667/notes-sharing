package com.notes.sharing.service;

import com.notes.sharing.dto.CreateNoteRequest;
import com.notes.sharing.dto.NoteResponse;
import com.notes.sharing.entity.Note;
import com.notes.sharing.entity.User;
import com.notes.sharing.exception.NotFoundException;
import com.notes.sharing.repository.NoteRepository;
import com.notes.sharing.repository.NoteSharingRepository;
import com.notes.sharing.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class NoteService {

    private final NoteRepository noteRepository;
    private final UserRepository userRepository;
    private final NoteSharingRepository sharingRepository;

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

        Note saved = noteRepository.save(note);
        return toResponse(saved);
    }

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
        if (title != null) note.setTitle(title);
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
        noteRepository.delete(note);
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
