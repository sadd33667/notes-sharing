package com.notes.sharing.dto;

import lombok.*;
import java.time.LocalDateTime;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class NotificationResponse {
    private Long notificationID;
    private String type;
    private Boolean isRead;
    private LocalDateTime issueDate;
    private Long noteId;
    private String notifierUsername;
}
