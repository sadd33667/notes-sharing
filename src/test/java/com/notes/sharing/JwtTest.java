package com.notes.sharing;

import com.notes.sharing.security.JwtService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class JwtTest {

    @Autowired
    private JwtService jwtService;

    @Test
    void tokenRoundTrip_returnsSameUserId() {
        String token = jwtService.generateToken(42L, "a@t.com");
        assertEquals(42L, jwtService.extractUserId(token));
    }

    @Test
    void tamperedToken_rejected() {
        String token = jwtService.generateToken(42L, "a@t.com");
        assertThrows(Exception.class, () -> jwtService.extractUserId(token + "x"));
    }
}
