package com.notes.sharing.security;

import com.notes.sharing.entity.User;
import com.notes.sharing.entity.UserSettings;
import com.notes.sharing.repository.UserRepository;
import com.notes.sharing.repository.UserSettingsRepository;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.UUID;

@Component
@RequiredArgsConstructor
public class OAuth2SuccessHandler implements AuthenticationSuccessHandler {

    private final UserRepository userRepository;
    private final UserSettingsRepository settingsRepository;
    private final JwtService jwtService;

    @Value("${app.oauth2.redirect:http://localhost:8080/api/auth/oauth2/done}")
    private String redirectBase;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
                                        Authentication authentication) throws IOException, ServletException {
        OAuth2User oauthUser = (OAuth2User) authentication.getPrincipal();
        String email = oauthUser.getAttribute("email");
        if (email == null) {
            String login = oauthUser.getAttribute("login");
            email = (login != null ? login : "oauth-user") + "@oauth.local";
        }
        String name = oauthUser.getAttribute("name");

        User user = userRepository.findByEmail(email).orElseGet(() -> {
            User created = userRepository.save(User.builder()
                    .username(name != null ? name : email)
                    .email(email)
                    .passwordHash("{oauth2}" + UUID.randomUUID())
                    .build());
            settingsRepository.save(UserSettings.builder().user(created).build());
            return created;
        });

        String token = jwtService.generateToken(user.getUserID(), user.getEmail());
        response.sendRedirect(redirectBase + "?token=" + token);
    }
}
