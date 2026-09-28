package com.notes.sharing.entity;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "notes")
@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class Note {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long noteID;

    @ManyToOne
    @JoinColumn(name = "ownerID", nullable = false)
    private User owner;

    private String title;
    private String noteType; // text, checklist, image
    private String color;

    @Builder.Default
    private Boolean isPinned = false;

    private LocalDateTime createdAt;
    private LocalDateTime startDate;
    private LocalDateTime endDate;
    private Integer notifyThreshold;
    private String visibility; // private, public, shared

    @PrePersist
    public void prePersist() {
        if (createdAt == null) createdAt = LocalDateTime.now();
    }
}
