package com.notes.sharing;

import com.notes.sharing.entity.User;
import com.notes.sharing.repository.UserRepository;
import com.notes.sharing.service.FriendshipService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class FriendshipTest {

    @Autowired
    private FriendshipService friendshipService;

    @Autowired
    private UserRepository userRepository;

    @Test
    void requestAcceptThenFriends() {
        User a = userRepository.save(User.builder()
                .username("a" + System.nanoTime()).email("a" + System.nanoTime() + "@t.com").passwordHash("h").build());
        User b = userRepository.save(User.builder()
                .username("b" + System.nanoTime()).email("b" + System.nanoTime() + "@t.com").passwordHash("h").build());

        var req = friendshipService.sendRequest(a.getUserID(), b.getUsername());
        assertEquals("pending", req.getStatus());
        assertTrue(friendshipService.pendingReceived(b.getUserID()).stream()
                .anyMatch(r -> r.getRelationshipID().equals(req.getRelationshipID())));

        friendshipService.respond(req.getRelationshipID(), b.getUserID(), true);

        assertEquals(1, friendshipService.myFriends(a.getUserID()).size());
        assertEquals("b".substring(0, 1) + b.getUsername().substring(1), friendshipService.myFriends(a.getUserID()).get(0).getFriendUsername());
    }
}
