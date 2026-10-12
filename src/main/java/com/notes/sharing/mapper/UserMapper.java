package com.notes.sharing.mapper;

import com.notes.sharing.dto.FriendshipResponse;
import com.notes.sharing.dto.ProfileResponse;
import com.notes.sharing.dto.SettingsResponse;
import com.notes.sharing.entity.Friendship;
import com.notes.sharing.entity.User;
import com.notes.sharing.entity.UserSettings;
import org.springframework.stereotype.Component;

// One place that turns users and friends into API responses.
@Component
public class UserMapper {

    public ProfileResponse toProfile(User u) {
        return ProfileResponse.builder()
                .userID(u.getUserID()).username(u.getUsername()).userTag(u.getUserTag())
                .email(u.getEmail()).bio(u.getBio()).phoneNumber(u.getPhoneNumber())
                .image(u.getImage()).secondaryEmail(u.getSecondaryEmail())
                .build();
    }

    public SettingsResponse toSettings(UserSettings s) {
        return SettingsResponse.builder()
                .theme(s.getTheme()).language(s.getLanguage())
                .twoFactorEnabled(s.getTwoFactorEnabled())
                .notificationsEnabled(s.getNotificationsEnabled())
                .build();
    }

    public FriendshipResponse toFriendship(Friendship f, Long viewerId) {
        User other = f.getFUser().getUserID().equals(viewerId) ? f.getSUser() : f.getFUser();
        return FriendshipResponse.builder()
                .relationshipID(f.getRelationshipID())
                .friendUsername(other.getUsername())
                .status(f.getStatus())
                .build();
    }
}
