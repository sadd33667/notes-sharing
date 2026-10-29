package com.notes.sharing.dto;

import lombok.*;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class ShareResponse {
    private Long sharingID;
    private Long userId;
    private String username;
    private String permission;
    private Boolean isFavorite;
}