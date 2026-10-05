package com.notes.sharing.dto;

import lombok.*;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class FriendshipResponse {
    private Long relationshipID;
    private String friendUsername;
    private String status;
}
