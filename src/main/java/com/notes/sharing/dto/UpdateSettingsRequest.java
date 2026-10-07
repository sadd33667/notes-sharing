package com.notes.sharing.dto;

import lombok.*;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class UpdateSettingsRequest {
    private String theme;
    private String language;
    private Boolean twoFactorEnabled;
    private Boolean notificationsEnabled;
}
