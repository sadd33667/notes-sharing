package com.notes.sharing.security;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import java.util.Collection;
import java.util.List;
// Spring's own user object. I only fill it, Spring does the checking.
public class UserPrincipal implements UserDetails {
    private final Long userId;
    private final String email;
    private final String passwordHash;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority("USER"));
    }

    @Override
    public String getPassword() {
        return passwordHash;
    }

    @Override
    public String getUsername() {
        return email;
    }
    public Long getUserId() {
        return this.userId;
    }
    public String getEmail() {
        return this.email;
    }
    public String getPasswordHash() {
        return this.passwordHash;
    }
    public UserPrincipal(final Long userId, final String email, final String passwordHash) {
        this.userId = userId;
        this.email = email;
        this.passwordHash = passwordHash;
    }
}
