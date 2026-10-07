package com.notes.sharing.service;

import com.notes.sharing.dto.ProfileResponse;
import com.notes.sharing.dto.SettingsResponse;
import com.notes.sharing.dto.UpdateProfileRequest;
import com.notes.sharing.dto.UpdateSettingsRequest;
import com.notes.sharing.entity.User;
import com.notes.sharing.entity.UserSettings;
import com.notes.sharing.exception.NotFoundException;
import com.notes.sharing.repository.FriendshipRepository;
import com.notes.sharing.repository.NoteRepository;
import com.notes.sharing.repository.NoteSharingRepository;
import com.notes.sharing.repository.NotificationRepository;
import com.notes.sharing.repository.UserRepository;
import com.notes.sharing.repository.UserSettingsRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final UserSettingsRepository settingsRepository;
    private final NoteRepository noteRepository;
    private final NoteSharingRepository sharingRepository;
    private final FriendshipRepository friendshipRepository;
    private final NotificationRepository notificationRepository;

    public ProfileResponse getProfile(Long userId) {
        return toProfile(getUser(userId));
    }

    @Transactional
    public ProfileResponse updateProfile(Long userId, UpdateProfileRequest body) {
        User user = getUser(userId);
        if (body.getBio() != null) user.setBio(body.getBio());
        if (body.getPhoneNumber() != null) user.setPhoneNumber(body.getPhoneNumber());
        if (body.getImage() != null) user.setImage(body.getImage());
        if (body.getSecondaryEmail() != null) user.setSecondaryEmail(body.getSecondaryEmail());
        return toProfile(userRepository.save(user));
    }

    public SettingsResponse getSettings(Long userId) {
        UserSettings s = settingsRepository.findById(userId).orElseGet(() ->
                UserSettings.builder().user(getUser(userId)).build());
        return toSettings(s);
    }

    @Transactional
    public SettingsResponse updateSettings(Long userId, UpdateSettingsRequest body) {
        UserSettings s = settingsRepository.findById(userId).orElseGet(() ->
                UserSettings.builder().user(getUser(userId)).build());
        if (body.getTheme() != null) s.setTheme(body.getTheme());
        if (body.getLanguage() != null) s.setLanguage(body.getLanguage());
        if (body.getTwoFactorEnabled() != null) s.setTwoFactorEnabled(body.getTwoFactorEnabled());
        if (body.getNotificationsEnabled() != null)
            s.setNotificationsEnabled(body.getNotificationsEnabled());
        return toSettings(settingsRepository.save(s));
    }

    private User getUser(Long userId) {
        return userRepository.findById(userId)
                .orElseThrow(() -> new NotFoundException("User not found"));
    }

    @Transactional
    public void deleteAccount(Long userId) {
        notificationRepository.deleteByUser(userId);
        noteRepository.findByOwner_UserID(userId)
                .forEach(n -> notificationRepository.deleteByNote(n.getNoteID()));
        sharingRepository.deleteByUser(userId);
        friendshipRepository.deleteByUser(userId);
        userRepository.delete(getUser(userId));
    }

    private static ProfileResponse toProfile(User u) {
        return ProfileResponse.builder()
                .userID(u.getUserID()).username(u.getUsername()).userTag(u.getUserTag())
                .email(u.getEmail()).bio(u.getBio()).phoneNumber(u.getPhoneNumber())
                .image(u.getImage()).secondaryEmail(u.getSecondaryEmail())
                .build();
    }

    private static SettingsResponse toSettings(UserSettings s) {
        return SettingsResponse.builder()
                .theme(s.getTheme()).language(s.getLanguage())
                .twoFactorEnabled(s.getTwoFactorEnabled())
                .notificationsEnabled(s.getNotificationsEnabled())
                .build();
    }
}
