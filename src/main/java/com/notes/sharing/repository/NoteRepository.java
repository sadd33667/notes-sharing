package com.notes.sharing.repository;

import com.notes.sharing.entity.Note;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;

public interface NoteRepository extends JpaRepository<Note, Long> {
    List<Note> findByOwner_UserID(Long ownerId);

    @Query("SELECT n FROM Note n WHERE n.owner.userID = :ownerId AND n.startDate >= :from AND n.startDate <= :to ORDER BY n.startDate")
    List<Note> findUpcoming(@Param("ownerId") Long ownerId,
                           @Param("from") LocalDateTime from,
                           @Param("to") LocalDateTime to);

    @Query("SELECT n FROM Note n WHERE n.owner.userID = :ownerId AND (:type IS NULL OR n.noteType = :type) ORDER BY n.createdAt DESC")
    List<Note> findMineFiltered(@Param("ownerId") Long ownerId, @Param("type") String type);
}
