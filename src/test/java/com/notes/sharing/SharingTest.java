package com.notes.sharing;

import com.notes.sharing.dto.CreateNoteRequest;
import com.notes.sharing.dto.NoteResponse;
import com.notes.sharing.entity.User;
import com.notes.sharing.repository.UserRepository;
import com.notes.sharing.service.NoteService;
import com.notes.sharing.service.SharingService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class SharingTest {

    @Autowired
    private SharingService sharingService;

    @Autowired
    private NoteService noteService;

    @Autowired
    private UserRepository userRepository;

    @Test
    void shareThenFriendCanRead() {
        User owner = userRepository.save(User.builder()
                .username("owner").email("o" + System.nanoTime() + "@t.com").passwordHash("h").build());
        User friend = userRepository.save(User.builder()
                .username("friend").email("f" + System.nanoTime() + "@t.com").passwordHash("h").build());

        NoteResponse note = noteService.createNote(CreateNoteRequest.builder()
                .ownerId(owner.getUserID()).title("Shared").contents("hi").build());

        // friend cannot read before share
        assertThrows(Exception.class, () -> noteService.getNote(note.getNoteID(), friend.getUserID()));

        sharingService.share(note.getNoteID(), owner.getUserID(), friend.getUserID(), "view");

        // friend can read after share
        NoteResponse seen = noteService.getNote(note.getNoteID(), friend.getUserID());
        assertEquals("Shared", seen.getTitle());
        assertEquals(1, sharingService.sharedWithMe(friend.getUserID()).size());

        sharingService.unshare(note.getNoteID(), owner.getUserID(), friend.getUserID());
        assertThrows(Exception.class, () -> noteService.getNote(note.getNoteID(), friend.getUserID()));
    }
}
