package com.notes.sharing;

import com.notes.sharing.dto.CreateNoteRequest;
import com.notes.sharing.dto.NoteResponse;
import com.notes.sharing.entity.User;
import com.notes.sharing.repository.UserRepository;
import com.notes.sharing.service.NoteService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class CreateNoteTest {

    @Autowired
    private NoteService noteService;

    @Autowired
    private UserRepository userRepository;

    @Test
    void createNote_returnsSavedNote() {
        String tag = String.valueOf(System.nanoTime());
        User user = userRepository.save(User.builder()
                .username("sara" + tag)
                .email("sara" + tag + "@test.com")
                .passwordHash("hash")
                .build());

        CreateNoteRequest req = CreateNoteRequest.builder()
                .ownerId(user.getUserID())
                .title("First note")
                .contents("Hello world")
                .build();

        NoteResponse res = noteService.createNote(req);

        assertNotNull(res.getNoteID());
        assertEquals("First note", res.getTitle());
        assertEquals(user.getUserID(), res.getOwnerId());
    }
}
