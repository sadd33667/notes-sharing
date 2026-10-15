package com.notes.sharing.dto;

import lombok.*;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class UpdateProfileRequest {
    private String bio;
    private String phoneNumber;
    private String image;
    private String secondaryEmail;
}
