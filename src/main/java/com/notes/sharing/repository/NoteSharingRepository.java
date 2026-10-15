package com.notes.sharing.repository;

import com.notes.sharing.entity.NoteSharing;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import java.util.List;

public interface NoteSharingRepository extends JpaRepository<NoteSharing, Long> {
    List<NoteSharing> findByUser_UserID(Long userId);
    List<NoteSharing> findByNote_NoteID(Long noteId);

    @Query("DELETE FROM NoteSharing s WHERE s.note.noteID = :noteId")
    @Modifying
    void deleteByNote(@Param("noteId") Long noteId);

    @Query("DELETE FROM NoteSharing s WHERE s.user.userID = :userId")
    @Modifying
    void deleteByUser(@Param("userId") Long userId);
}
