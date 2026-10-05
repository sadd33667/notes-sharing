package com.notes.sharing.service;

import com.notes.sharing.dto.ProfileResponse;
import com.notes.sharing.dto.SettingsResponse;
import com.notes.sharing.entity.User;
import com.notes.sharing.entity.UserSettings;
import com.notes.sharing.exception.NotFoundException;
import com.notes.sharing.repository.UserRepository;
import com.notes.sharing.repository.UserSettingsRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Map;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final UserSettingsRepository settingsRepository;

    public ProfileResponse getProfile(Long userId) {
        return toProfile(getUser(userId));
    }

    @Transactional
    public ProfileResponse updateProfile(Long userId, Map<String, String> body) {
        User user = getUser(userId);
        if (body.containsKey("bio")) user.setBio(body.get("bio"));
        if (body.containsKey("phoneNumber")) user.setPhoneNumber(body.get("phoneNumber"));
        if (body.containsKey("image")) user.setImage(body.get("image"));
        if (body.containsKey("secondaryEmail")) user.setSecondaryEmail(body.get("secondaryEmail"));
        return toProfile(userRepository.save(user));
    }

    public SettingsResponse getSettings(Long userId) {
        UserSettings s = settingsRepository.findById(userId).orElseGet(() ->
                UserSettings.builder().user(getUser(userId)).build());
        return toSettings(s);
    }

    @Transactional
    public SettingsResponse updateSettings(Long userId, Map<String, Object> body) {
        UserSettings s = settingsRepository.findById(userId).orElseGet(() ->
                UserSettings.builder().user(getUser(userId)).build());
        if (body.containsKey("theme")) s.setTheme((String) body.get("theme"));
        if (body.containsKey("language")) s.setLanguage((String) body.get("language"));
        if (body.containsKey("twoFactorEnabled")) s.setTwoFactorEnabled((Boolean) body.get("twoFactorEnabled"));
        if (body.containsKey("notificationsEnabled"))
            s.setNotificationsEnabled((Boolean) body.get("notificationsEnabled"));
        return toSettings(settingsRepository.save(s));
    }

    private User getUser(Long userId) {
        return userRepository.findById(userId)
                .orElseThrow(() -> new NotFoundException("User not found"));
    }

    @Transactional
    public void deleteAccount(Long userId) {
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
