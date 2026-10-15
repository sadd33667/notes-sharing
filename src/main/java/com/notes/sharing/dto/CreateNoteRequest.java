package com.notes.sharing.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class CreateNoteRequest {
    @NotBlank
    private String title;

    private String contents;
    private String noteType;
    private String color;
    private String visibility;
}
