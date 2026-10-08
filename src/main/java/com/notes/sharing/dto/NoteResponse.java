package com.notes.sharing.dto;

import lombok.*;
import java.time.LocalDateTime;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class NoteResponse {
    private Long noteID;
    private Long ownerId;
    private String title;
    private String contents;
    private String noteType;
    private String color;
    private Boolean isPinned;
    private LocalDateTime createdAt;
    private String visibility;
}
