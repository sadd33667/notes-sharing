package com.notes.sharing.controller;

import com.notes.sharing.dto.CreateNoteRequest;
import com.notes.sharing.dto.NoteResponse;
import com.notes.sharing.service.NoteService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/notes")
@RequiredArgsConstructor
public class NoteController {

    private final NoteService noteService;

    @PostMapping
    public ResponseEntity<NoteResponse> create(@Valid @RequestBody CreateNoteRequest req) {
        return ResponseEntity.status(HttpStatus.CREATED).body(noteService.createNote(req));
    }

    @GetMapping("/{id}")
    public ResponseEntity<NoteResponse> getOne(
            @PathVariable Long id,
            org.springframework.security.core.Authentication auth) {
        Long requesterId = (Long) auth.getPrincipal();
        return ResponseEntity.ok(noteService.getNote(id, requesterId));
    }

    @PutMapping("/{id}")
    public ResponseEntity<NoteResponse> update(
            @PathVariable Long id,
            @RequestBody java.util.Map<String, String> body,
            org.springframework.security.core.Authentication auth) {
        Long requesterId = (Long) auth.getPrincipal();
        return ResponseEntity.ok(noteService.updateNote(id, requesterId, body.get("title"), body.get("contents")));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(
            @PathVariable Long id,
            org.springframework.security.core.Authentication auth) {
        Long requesterId = (Long) auth.getPrincipal();
        noteService.deleteNote(id, requesterId);
        return ResponseEntity.noContent().build();
    }

    @GetMapping
    public ResponseEntity<List<NoteResponse>> myNotes(
            @RequestParam(required = false) String type,
            org.springframework.security.core.Authentication auth) {
        Long userId = (Long) auth.getPrincipal();
        if (type != null) {
            return ResponseEntity.ok(noteService.myNotesByType(userId, type));
        }
        return ResponseEntity.ok(noteService.myNotes(userId));
    }

    @GetMapping("/upcoming")
    public ResponseEntity<List<NoteResponse>> upcoming(
            @RequestParam String from,
            @RequestParam String to,
            org.springframework.security.core.Authentication auth) {
        return ResponseEntity.ok(noteService.upcoming(
                (Long) auth.getPrincipal(),
                java.time.LocalDateTime.parse(from),
                java.time.LocalDateTime.parse(to)));
    }

    @PutMapping("/{id}/pin")
    public ResponseEntity<NoteResponse> setPinned(
            @PathVariable Long id,
            @RequestBody java.util.Map<String, Boolean> body,
            org.springframework.security.core.Authentication auth) {
        Boolean pinned = body.getOrDefault("isPinned", true);
        return ResponseEntity.ok(noteService.setPinned(id, (Long) auth.getPrincipal(), pinned));
    }
}
