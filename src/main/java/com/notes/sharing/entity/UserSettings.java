package com.notes.sharing.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "user_settings")
@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class UserSettings {
    @Id
    private Long userID;

    @OneToOne
    @MapsId
    @JoinColumn(name = "userID")
    private User user;

    @Builder.Default
    private String theme = "light";
    @Builder.Default
    private String language = "en";
    @Builder.Default
    private Boolean twoFactorEnabled = false;
    @Builder.Default
    private Boolean notificationsEnabled = true;
}
