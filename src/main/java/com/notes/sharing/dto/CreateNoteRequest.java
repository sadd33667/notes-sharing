package com.notes.sharing.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class CreateNoteRequest {
    @NotNull
    private Long ownerId;

    @NotBlank
    private String title;

    private String contents;
    private String noteType;
    private String color;
    private String visibility;
}
