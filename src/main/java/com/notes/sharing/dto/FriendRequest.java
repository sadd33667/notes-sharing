package com.notes.sharing.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class FriendRequest {
    @NotBlank
    private String username;
}
