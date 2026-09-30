package com.notes.sharing.repository;

import com.notes.sharing.entity.Notification;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface NotificationRepository extends JpaRepository<Notification, Long> {
    List<Notification> findByNotified_UserIDOrderByIssueDateDesc(Long userId);
}
