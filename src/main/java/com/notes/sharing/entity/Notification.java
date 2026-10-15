package com.notes.sharing.entity;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "notifications")
@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class Notification {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long notificationID;

    @ManyToOne
    @JoinColumn(name = "notifierUserID")
    private User notifier;

    @ManyToOne
    @JoinColumn(name = "notifiedUserID")
    private User notified;

    private String type;

    @ManyToOne
    @JoinColumn(name = "noteID")
    private Note note;

    @ManyToOne
    @JoinColumn(name = "relationshipID")
    private Friendship friendship;

    private LocalDateTime issueDate;

    @Builder.Default
    private Boolean isRead = false;

    @PrePersist
    public void prePersist() {
        if (issueDate == null) issueDate = LocalDateTime.now();
    }
}
