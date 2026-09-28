package com.notes.sharing.entity;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

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
    @Column(length = 5000)
    private String contents;
    private String noteType; // text, checklist, image
    private String color;

    @Builder.Default
    private Boolean isPinned = false;

    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private LocalDateTime startDate;
    private LocalDateTime endDate;
    private Integer notifyThreshold;
    private String visibility; // private, public, shared

    // Relations
    @OneToMany(mappedBy = "note", cascade = CascadeType.ALL)
    @Builder.Default
    private List<NoteSharing> sharedWith = new ArrayList<>();

    @PrePersist
    public void prePersist() {
        if (createdAt == null) createdAt = LocalDateTime.now();
        updatedAt = LocalDateTime.now();
    }

    @PreUpdate
    public void preUpdate() {
        updatedAt = LocalDateTime.now();
    }
}
