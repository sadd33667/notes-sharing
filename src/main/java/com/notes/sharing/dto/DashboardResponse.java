package com.notes.sharing.dto;

import lombok.*;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class DashboardResponse {
    private long myNotesCount;
    private long sharedWithMeCount;
    private long friendsCount;
}
