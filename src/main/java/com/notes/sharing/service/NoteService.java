package com.notes.sharing.service;

import com.notes.sharing.dto.CreateNoteRequest;
import com.notes.sharing.dto.NoteResponse;
import com.notes.sharing.entity.Note;
import com.notes.sharing.entity.User;
import com.notes.sharing.exception.NotFoundException;
import com.notes.sharing.repository.NoteRepository;
import com.notes.sharing.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class NoteService {

    private final NoteRepository noteRepository;
    private final UserRepository userRepository;

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
