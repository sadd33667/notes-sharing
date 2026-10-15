package com.notes.sharing.dto;

import lombok.*;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class UpdateNoteRequest {
    private String title;
    private String contents;
}
