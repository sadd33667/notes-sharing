package com.notes.sharing.entity;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "friendships")
@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class Friendship {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long relationshipID;

    @ManyToOne
    @JoinColumn(name = "fUserID", nullable = false)
    private User fUser;

    @ManyToOne
    @JoinColumn(name = "sUserID", nullable = false)
    private User sUser;

    private String status; // pending, accepted
    private LocalDateTime createdAt;

    @PrePersist
    public void prePersist() {
        if (createdAt == null) createdAt = LocalDateTime.now();
    }
}
