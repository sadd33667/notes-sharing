package com.notes.sharing;

import com.notes.sharing.dto.LoginRequest;
import com.notes.sharing.dto.RegisterRequest;
import com.notes.sharing.dto.AuthResponse;
import com.notes.sharing.service.AuthService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class AuthTest {

    @Autowired
    private AuthService authService;

    @Test
    void registerThenLogin_returnsToken() {
        String email = "auth" + System.nanoTime() + "@test.com";

        AuthResponse reg = authService.register(RegisterRequest.builder()
                .username("sara")
                .email(email)
                .password("secret123")
                .build());

        assertNotNull(reg.getToken());
        assertNotNull(reg.getUserId());

        AuthResponse login = authService.login(LoginRequest.builder()
                .email(email)
                .password("secret123")
                .build());

        assertNotNull(login.getToken());
        assertEquals(reg.getUserId(), login.getUserId());
    }
}
