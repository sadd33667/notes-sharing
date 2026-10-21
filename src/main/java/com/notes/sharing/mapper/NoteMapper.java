package com.notes.sharing.mapper;

import com.notes.sharing.dto.NoteResponse;
import com.notes.sharing.entity.Note;
import org.springframework.stereotype.Component;

// One place that turns notes into API responses.
@Component
public class NoteMapper {

    public NoteResponse toResponse(Note n) {
        return NoteResponse.builder()
                .noteID(n.getNoteID())
                .userNoteNumber(n.getUserNoteNumber())
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
