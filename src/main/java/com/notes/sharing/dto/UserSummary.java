package com.notes.sharing.dto;

import lombok.*;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class UserSummary {
    private Long userID;
    private String username;
    private String userTag;
}