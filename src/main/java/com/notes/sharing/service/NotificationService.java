package com.notes.sharing.service;

import com.notes.sharing.entity.Notification;
import com.notes.sharing.repository.NotificationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class NotificationService {

    private final NotificationRepository notificationRepository;

    public List<Notification> myNotifications(Long userId) {
        return notificationRepository.findByNotified_UserIDOrderByIssueDateDesc(userId);
    }

    @Transactional
    public void markRead(Long notificationId, Long userId) {
        Notification n = notificationRepository.findById(notificationId)
                .orElseThrow(() -> new com.notes.sharing.exception.NotFoundException("Not found"));
        if (!n.getNotified().getUserID().equals(userId)) {
            throw new com.notes.sharing.exception.NotFoundException("Not found");
        }
        n.setIsRead(true);
        notificationRepository.save(n);
    }
}
