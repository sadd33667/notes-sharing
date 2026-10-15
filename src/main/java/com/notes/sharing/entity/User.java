package com.notes.sharing.entity;

import jakarta.persistence.*;
import lombok.*;
import com.fasterxml.jackson.annotation.JsonIgnore;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "users")
@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long userID;

    @Column(nullable = false, unique = true)
    private String username;

    private String userTag; // like sara#1234

    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = false)
    private String passwordHash;

    @Column(length = 500)
    private String bio;

    private LocalDate birthDate;

    private String gender;
    private String phoneNumber;
    private String image;
    private String secondaryEmail;

    private LocalDateTime createdAt;

    // Relations - my vision
    @OneToOne(mappedBy = "user", cascade = CascadeType.ALL)
    @JsonIgnore
    private UserSettings settings;

    @OneToMany(mappedBy = "owner", cascade = CascadeType.ALL)
    @Builder.Default
    @JsonIgnore
    private List<Note> notes = new ArrayList<>();

    @OneToMany(mappedBy = "fUser")
    @Builder.Default
    @JsonIgnore
    private List<Friendship> friendshipsSent = new ArrayList<>();

    @OneToMany(mappedBy = "sUser")
    @Builder.Default
    @JsonIgnore
    private List<Friendship> friendshipsReceived = new ArrayList<>();

    @OneToMany(mappedBy = "user")
    @Builder.Default
    @JsonIgnore
    private List<NoteSharing> myShares = new ArrayList<>();

    @PrePersist
    public void prePersist() {
        if (createdAt == null) createdAt = LocalDateTime.now();
    }
}
