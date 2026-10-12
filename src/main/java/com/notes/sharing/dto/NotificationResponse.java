package com.notes.sharing.dto;

import java.time.LocalDateTime;

public class NotificationResponse {
    private Long notificationID;
    private String type;
    private Boolean isRead;
    private LocalDateTime issueDate;
    private Long noteId;
    private String notifierUsername;
    public static class NotificationResponseBuilder {
        private Long notificationID;
        private String type;
        private Boolean isRead;
        private LocalDateTime issueDate;
        private Long noteId;
        private String notifierUsername;
        NotificationResponseBuilder() {
        }

        /**
         * @return {@code this}.
         */
        public NotificationResponse.NotificationResponseBuilder notificationID(final Long notificationID) {
            this.notificationID = notificationID;
            return this;
        }

        /**
         * @return {@code this}.
         */
        public NotificationResponse.NotificationResponseBuilder type(final String type) {
            this.type = type;
            return this;
        }

        /**
         * @return {@code this}.
         */
        public NotificationResponse.NotificationResponseBuilder isRead(final Boolean isRead) {
            this.isRead = isRead;
            return this;
        }

        /**
         * @return {@code this}.
         */
        public NotificationResponse.NotificationResponseBuilder issueDate(final LocalDateTime issueDate) {
            this.issueDate = issueDate;
            return this;
        }

        /**
         * @return {@code this}.
         */
        public NotificationResponse.NotificationResponseBuilder noteId(final Long noteId) {
            this.noteId = noteId;
            return this;
        }

        /**
         * @return {@code this}.
         */
        public NotificationResponse.NotificationResponseBuilder notifierUsername(final String notifierUsername) {
            this.notifierUsername = notifierUsername;
            return this;
        }
        public NotificationResponse build() {
            return new NotificationResponse(this.notificationID, this.type, this.isRead, this.issueDate, this.noteId, this.notifierUsername);
        }

        @java.lang.Override
        public java.lang.String toString() {
            return "NotificationResponse.NotificationResponseBuilder(notificationID=" + this.notificationID + ", type=" + this.type + ", isRead=" + this.isRead + ", issueDate=" + this.issueDate + ", noteId=" + this.noteId + ", notifierUsername=" + this.notifierUsername + ")";
        }
    }
    public static NotificationResponse.NotificationResponseBuilder builder() {
        return new NotificationResponse.NotificationResponseBuilder();
    }
    public Long getNotificationID() {
        return this.notificationID;
    }
    public String getType() {
        return this.type;
    }
    public Boolean getIsRead() {
        return this.isRead;
    }
    public LocalDateTime getIssueDate() {
        return this.issueDate;
    }
    public Long getNoteId() {
        return this.noteId;
    }
    public String getNotifierUsername() {
        return this.notifierUsername;
    }
    public void setNotificationID(final Long notificationID) {
        this.notificationID = notificationID;
    }
    public void setType(final String type) {
        this.type = type;
    }
    public void setIsRead(final Boolean isRead) {
        this.isRead = isRead;
    }
    public void setIssueDate(final LocalDateTime issueDate) {
        this.issueDate = issueDate;
    }
    public void setNoteId(final Long noteId) {
        this.noteId = noteId;
    }
    public void setNotifierUsername(final String notifierUsername) {
        this.notifierUsername = notifierUsername;
    }
    public NotificationResponse() {
    }
    public NotificationResponse(final Long notificationID, final String type, final Boolean isRead, final LocalDateTime issueDate, final Long noteId, final String notifierUsername) {
        this.notificationID = notificationID;
        this.type = type;
        this.isRead = isRead;
        this.issueDate = issueDate;
        this.noteId = noteId;
        this.notifierUsername = notifierUsername;
    }
}
