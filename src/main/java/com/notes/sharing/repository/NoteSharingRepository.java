package com.notes.sharing.repository;

import com.notes.sharing.entity.NoteSharing;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface NoteSharingRepository extends JpaRepository<NoteSharing, Long> {
    List<NoteSharing> findByUser_UserID(Long userId);
    List<NoteSharing> findByNote_NoteID(Long noteId);
}
