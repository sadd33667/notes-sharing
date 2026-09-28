package com.notes.sharing.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "note_sharing")
@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class NoteSharing {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long sharingID;

    @ManyToOne
    @JoinColumn(name = "userID", nullable = false)
    private User user;

    @ManyToOne
    @JoinColumn(name = "noteID", nullable = false)
    private Note note;

    private String permission; // view, edit

    @Builder.Default
    private Boolean isFavorite = false;
}
