package com.notes.sharing.security;

import com.notes.sharing.repository.UserRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
// Tells Spring how to load my users. Spring itself compares passwords with BCrypt.
@Service
public class AppUserDetailsService implements UserDetailsService {
    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        return userRepository.findByEmail(email.trim().toLowerCase()).map(u -> new UserPrincipal(u.getUserID(), u.getEmail(), u.getPasswordHash())).orElseThrow(() -> new UsernameNotFoundException("Invalid email or password"));
    }
    public AppUserDetailsService(final UserRepository userRepository) {
        this.userRepository = userRepository;
    }
}
