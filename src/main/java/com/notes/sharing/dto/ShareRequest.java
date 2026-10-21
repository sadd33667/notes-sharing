package com.notes.sharing.dto;

import jakarta.validation.constraints.NotNull;
import lombok.*;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class ShareRequest {
    @NotNull
    private Long userId;

    private String permission;
}
