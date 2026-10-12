package com.notes.sharing.entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "notifications")
public class Notification {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long notificationID;
    @ManyToOne
    @JoinColumn(name = "notifierUserID")
    private User notifier;
    @ManyToOne
    @JoinColumn(name = "notifiedUserID")
    private User notified;
    private String type;
    @ManyToOne
    @JoinColumn(name = "noteID")
    private Note note;
    @ManyToOne
    @JoinColumn(name = "relationshipID")
    private Friendship friendship;
    private LocalDateTime issueDate;
    private Boolean isRead;

    @PrePersist
    public void prePersist() {
        if (issueDate == null) issueDate = LocalDateTime.now();
    }
    private static Boolean $default$isRead() {
        return false;
    }
    public static class NotificationBuilder {
        private Long notificationID;
        private User notifier;
        private User notified;
        private String type;
        private Note note;
        private Friendship friendship;
        private LocalDateTime issueDate;
        private boolean isRead$set;
        private Boolean isRead$value;
        NotificationBuilder() {
        }

        /**
         * @return {@code this}.
         */
        public Notification.NotificationBuilder notificationID(final Long notificationID) {
            this.notificationID = notificationID;
            return this;
        }

        /**
         * @return {@code this}.
         */
        public Notification.NotificationBuilder notifier(final User notifier) {
            this.notifier = notifier;
            return this;
        }

        /**
         * @return {@code this}.
         */
        public Notification.NotificationBuilder notified(final User notified) {
            this.notified = notified;
            return this;
        }

        /**
         * @return {@code this}.
         */
        public Notification.NotificationBuilder type(final String type) {
            this.type = type;
            return this;
        }

        /**
         * @return {@code this}.
         */
        public Notification.NotificationBuilder note(final Note note) {
            this.note = note;
            return this;
        }

        /**
         * @return {@code this}.
         */
        public Notification.NotificationBuilder friendship(final Friendship friendship) {
            this.friendship = friendship;
            return this;
        }

        /**
         * @return {@code this}.
         */
        public Notification.NotificationBuilder issueDate(final LocalDateTime issueDate) {
            this.issueDate = issueDate;
            return this;
        }

        /**
         * @return {@code this}.
         */
        public Notification.NotificationBuilder isRead(final Boolean isRead) {
            this.isRead$value = isRead;
            isRead$set = true;
            return this;
        }
        public Notification build() {
            Boolean isRead$value = this.isRead$value;
            if (!this.isRead$set) isRead$value = Notification.$default$isRead();
            return new Notification(this.notificationID, this.notifier, this.notified, this.type, this.note, this.friendship, this.issueDate, isRead$value);
        }

        @java.lang.Override
        public java.lang.String toString() {
            return "Notification.NotificationBuilder(notificationID=" + this.notificationID + ", notifier=" + this.notifier + ", notified=" + this.notified + ", type=" + this.type + ", note=" + this.note + ", friendship=" + this.friendship + ", issueDate=" + this.issueDate + ", isRead$value=" + this.isRead$value + ")";
        }
    }
    public static Notification.NotificationBuilder builder() {
        return new Notification.NotificationBuilder();
    }
    public Long getNotificationID() {
        return this.notificationID;
    }
    public User getNotifier() {
        return this.notifier;
    }
    public User getNotified() {
        return this.notified;
    }
    public String getType() {
        return this.type;
    }
    public Note getNote() {
        return this.note;
    }
    public Friendship getFriendship() {
        return this.friendship;
    }
    public LocalDateTime getIssueDate() {
        return this.issueDate;
    }
    public Boolean getIsRead() {
        return this.isRead;
    }
    public void setNotificationID(final Long notificationID) {
        this.notificationID = notificationID;
    }
    public void setNotifier(final User notifier) {
        this.notifier = notifier;
    }
    public void setNotified(final User notified) {
        this.notified = notified;
    }
    public void setType(final String type) {
        this.type = type;
    }
    public void setNote(final Note note) {
        this.note = note;
    }
    public void setFriendship(final Friendship friendship) {
        this.friendship = friendship;
    }
    public void setIssueDate(final LocalDateTime issueDate) {
        this.issueDate = issueDate;
    }
    public void setIsRead(final Boolean isRead) {
        this.isRead = isRead;
    }
    public Notification() {
        this.isRead = Notification.$default$isRead();
    }
    public Notification(final Long notificationID, final User notifier, final User notified, final String type, final Note note, final Friendship friendship, final LocalDateTime issueDate, final Boolean isRead) {
        this.notificationID = notificationID;
        this.notifier = notifier;
        this.notified = notified;
        this.type = type;
        this.note = note;
        this.friendship = friendship;
        this.issueDate = issueDate;
        this.isRead = isRead;
    }
}
