package com.notes.sharing.repository;

import com.notes.sharing.entity.Note;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface NoteRepository extends JpaRepository<Note, Long> {
    List<Note> findByOwner_UserID(Long ownerId);
}
