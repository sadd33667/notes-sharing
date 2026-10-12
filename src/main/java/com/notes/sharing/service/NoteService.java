package com.notes.sharing.service;

import com.notes.sharing.dto.CreateNoteRequest;
import com.notes.sharing.dto.NoteResponse;
import com.notes.sharing.entity.Note;
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
import java.time.LocalDateTime;
import java.util.List;

@Service
public class NoteService {
    private final NoteRepository noteRepository;
    private final UserRepository userRepository;
    private final NoteSharingRepository sharingRepository;
    private final NotificationRepository notificationRepository;
    private final NoteMapper noteMapper;

    @Transactional
    public NoteResponse createNote(CreateNoteRequest req) {
        User owner = userRepository.findById(req.getOwnerId()).orElseThrow(() -> new NotFoundException(1001, "User not found: " + req.getOwnerId()));
        Note note = Note.builder().owner(owner).title(req.getTitle()).contents(req.getContents()).userNoteNumber(noteRepository.maxNumberForOwner(owner.getUserID()) + 1).noteType(req.getNoteType() != null ? req.getNoteType() : "text").color(req.getColor()).visibility(req.getVisibility() != null ? req.getVisibility() : "private").build();
        if (!"private".equalsIgnoreCase(note.getVisibility()) && !"public".equalsIgnoreCase(note.getVisibility()) && !"shared".equalsIgnoreCase(note.getVisibility())) {
            throw new ConflictException(3004, "Visibility must be private, public or shared");
        }
        note.setVisibility(note.getVisibility().toLowerCase());
        Note saved = noteRepository.save(note);
        return noteMapper.toResponse(saved);
    }

    @Transactional(readOnly = true)
    public NoteResponse getNote(Long noteId, Long requesterId) {
        Note note = noteRepository.findById(noteId).orElseThrow(() -> new NotFoundException(1002, "Note not found: " + noteId));
        boolean isOwner = note.getOwner().getUserID().equals(requesterId);
        boolean isShared = sharingRepository.findByNote_NoteID(noteId).stream().anyMatch(s -> s.getUser().getUserID().equals(requesterId));
        boolean isPublic = "public".equalsIgnoreCase(note.getVisibility());
        if (!isOwner && !isShared && !isPublic) {
            throw new ForbiddenException(2001, "This note is private and not shared with you");
        }
        return noteMapper.toResponse(note);
    }

    @Transactional
    public NoteResponse updateNote(Long noteId, Long requesterId, String title, String contents) {
        Note note = noteRepository.findById(noteId).orElseThrow(() -> new NotFoundException(1002, "Note not found: " + noteId));
        boolean isOwner = note.getOwner().getUserID().equals(requesterId);
        boolean canEdit = sharingRepository.findByNote_NoteID(noteId).stream().anyMatch(s -> s.getUser().getUserID().equals(requesterId) && "edit".equalsIgnoreCase(s.getPermission()));
        if (!isOwner && !canEdit) {
            throw new ForbiddenException(2002, "Only the owner or an editor can change this note");
        }
        if (title != null && !title.isBlank()) note.setTitle(title);
        if (contents != null) note.setContents(contents);
        return noteMapper.toResponse(noteRepository.save(note));
    }

    @Transactional
    public void deleteNote(Long noteId, Long requesterId) {
        Note note = noteRepository.findById(noteId).orElseThrow(() -> new NotFoundException(1002, "Note not found: " + noteId));
        if (!note.getOwner().getUserID().equals(requesterId)) {
            throw new ForbiddenException(2003, "Only the owner can delete this note");
        }
        notificationRepository.deleteByNote(noteId);
        sharingRepository.deleteByNote(noteId);
        noteRepository.delete(note);
    }

    @Transactional(readOnly = true)
    public List<NoteResponse> myNotes(Long ownerId) {
        return noteRepository.findMineFiltered(ownerId, null).stream().map(noteMapper::toResponse).toList();
    }

    public List<NoteResponse> myNotesByType(Long ownerId, String type) {
        return noteRepository.findMineFiltered(ownerId, type).stream().map(noteMapper::toResponse).toList();
    }

    @Transactional(readOnly = true)
    public NoteResponse getMyNoteByNumber(Long ownerId, int number) {
        Note note = noteRepository.findByOwnerAndNumber(ownerId, number).orElseThrow(() -> new NotFoundException(1002, "No note number " + number + " for you"));
        return noteMapper.toResponse(note);
    }

    @Transactional(readOnly = true)
    public List<NoteResponse> upcoming(Long ownerId, LocalDateTime from, LocalDateTime to) {
        if (from.isAfter(to)) {
            throw new ConflictException(3007, "From date must be before to date");
        }
        return noteRepository.findUpcoming(ownerId, from, to).stream().map(noteMapper::toResponse).toList();
    }

    @Transactional
    public NoteResponse setPinned(Long noteId, Long requesterId, boolean pinned) {
        Note note = noteRepository.findById(noteId).orElseThrow(() -> new NotFoundException(1002, "Note not found: " + noteId));
        if (!note.getOwner().getUserID().equals(requesterId)) {
            throw new ForbiddenException(2004, "Only the owner can pin this note");
        }
        note.setIsPinned(pinned);
        return noteMapper.toResponse(noteRepository.save(note));
    }
    public NoteService(final NoteRepository noteRepository, final UserRepository userRepository, final NoteSharingRepository sharingRepository, final NotificationRepository notificationRepository, final NoteMapper noteMapper) {
        this.noteRepository = noteRepository;
        this.userRepository = userRepository;
        this.sharingRepository = sharingRepository;
        this.notificationRepository = notificationRepository;
        this.noteMapper = noteMapper;
    }
}
