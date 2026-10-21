package com.notes.sharing.repository;

import com.notes.sharing.entity.Notification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import java.util.List;

public interface NotificationRepository extends JpaRepository<Notification, Long> {
    List<Notification> findByNotified_UserIDOrderByIssueDateDesc(Long userId);

    @Query("DELETE FROM Notification n WHERE n.note.noteID = :noteId")
    @Modifying
    void deleteByNote(@Param("noteId") Long noteId);

    @Query("DELETE FROM Notification n WHERE n.friendship.relationshipID = :relationshipId")
    @Modifying
    void deleteByFriendship(@Param("relationshipId") Long relationshipId);

    @Query("DELETE FROM Notification n WHERE n.notified.userID = :userId OR n.notifier.userID = :userId")
    @Modifying
    void deleteByUser(@Param("userId") Long userId);
}
