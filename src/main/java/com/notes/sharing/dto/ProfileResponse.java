package com.notes.sharing.dto;

import lombok.*;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class ProfileResponse {
    private Long userID;
    private String username;
    private String userTag;
    private String email;
    private String bio;
    private String phoneNumber;
    private String image;
    private String secondaryEmail;
}
