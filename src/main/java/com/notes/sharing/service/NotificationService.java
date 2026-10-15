package com.notes.sharing.service;

import com.notes.sharing.dto.NotificationResponse;
import com.notes.sharing.entity.Notification;
import com.notes.sharing.exception.ErrorCode;
import com.notes.sharing.exception.SystemException;
import com.notes.sharing.repository.NotificationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class NotificationService {

    private final NotificationRepository notificationRepository;

    @Transactional(readOnly = true)
    public List<NotificationResponse> myNotifications(Long userId) {
        return notificationRepository.findByNotified_UserIDOrderByIssueDateDesc(userId).stream()
                .map(n -> NotificationResponse.builder()
                        .notificationID(n.getNotificationID())
                        .type(n.getType())
                        .isRead(n.getIsRead())
                        .issueDate(n.getIssueDate())
                        .noteId(n.getNote() != null ? n.getNote().getNoteID() : null)
                        .notifierUsername(n.getNotifier() != null ? n.getNotifier().getUsername() : null)
                        .build())
                .toList();
    }

    @Transactional
    public void markRead(Long notificationId, Long userId) {
        Notification n = notificationRepository.findById(notificationId)
                .orElseThrow(() -> new com.notes.sharing.exception.SystemException(com.notes.sharing.exception.ErrorCode.NOTIFICATION_NOT_FOUND, "Notification not found"));
        if (!n.getNotified().getUserID().equals(userId)) {
            throw new SystemException(ErrorCode.NOTIFICATION_DENIED, "This notification is not yours");
        }
        n.setIsRead(true);
        notificationRepository.save(n);
    }
}
