package com.notes.sharing.dto;

import java.time.LocalDateTime;

public class NoteResponse {
    private Long noteID;
    private Integer userNoteNumber;
    private Long ownerId;
    private String title;
    private String contents;
    private String noteType;
    private String color;
    private Boolean isPinned;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private String visibility;
    public static class NoteResponseBuilder {
        private Long noteID;
        private Integer userNoteNumber;
        private Long ownerId;
        private String title;
        private String contents;
        private String noteType;
        private String color;
        private Boolean isPinned;
        private LocalDateTime createdAt;
        private LocalDateTime updatedAt;
        private String visibility;
        NoteResponseBuilder() {
        }

        /**
         * @return {@code this}.
         */
        public NoteResponse.NoteResponseBuilder noteID(final Long noteID) {
            this.noteID = noteID;
            return this;
        }

        /**
         * @return {@code this}.
         */
        public NoteResponse.NoteResponseBuilder userNoteNumber(final Integer userNoteNumber) {
            this.userNoteNumber = userNoteNumber;
            return this;
        }

        /**
         * @return {@code this}.
         */
        public NoteResponse.NoteResponseBuilder ownerId(final Long ownerId) {
            this.ownerId = ownerId;
            return this;
        }

        /**
         * @return {@code this}.
         */
        public NoteResponse.NoteResponseBuilder title(final String title) {
            this.title = title;
            return this;
        }

        /**
         * @return {@code this}.
         */
        public NoteResponse.NoteResponseBuilder contents(final String contents) {
            this.contents = contents;
            return this;
        }

        /**
         * @return {@code this}.
         */
        public NoteResponse.NoteResponseBuilder noteType(final String noteType) {
            this.noteType = noteType;
            return this;
        }

        /**
         * @return {@code this}.
         */
        public NoteResponse.NoteResponseBuilder color(final String color) {
            this.color = color;
            return this;
        }

        /**
         * @return {@code this}.
         */
        public NoteResponse.NoteResponseBuilder isPinned(final Boolean isPinned) {
            this.isPinned = isPinned;
            return this;
        }

        /**
         * @return {@code this}.
         */
        public NoteResponse.NoteResponseBuilder createdAt(final LocalDateTime createdAt) {
            this.createdAt = createdAt;
            return this;
        }

        /**
         * @return {@code this}.
         */
        public NoteResponse.NoteResponseBuilder updatedAt(final LocalDateTime updatedAt) {
            this.updatedAt = updatedAt;
            return this;
        }

        /**
         * @return {@code this}.
         */
        public NoteResponse.NoteResponseBuilder visibility(final String visibility) {
            this.visibility = visibility;
            return this;
        }
        public NoteResponse build() {
            return new NoteResponse(this.noteID, this.userNoteNumber, this.ownerId, this.title, this.contents, this.noteType, this.color, this.isPinned, this.createdAt, this.updatedAt, this.visibility);
        }

        @java.lang.Override
        public java.lang.String toString() {
            return "NoteResponse.NoteResponseBuilder(noteID=" + this.noteID + ", userNoteNumber=" + this.userNoteNumber + ", ownerId=" + this.ownerId + ", title=" + this.title + ", contents=" + this.contents + ", noteType=" + this.noteType + ", color=" + this.color + ", isPinned=" + this.isPinned + ", createdAt=" + this.createdAt + ", visibility=" + this.visibility + ")";
        }
    }
    public static NoteResponse.NoteResponseBuilder builder() {
        return new NoteResponse.NoteResponseBuilder();
    }
    public Long getNoteID() {
        return this.noteID;
    }
    public Integer getUserNoteNumber() {
        return this.userNoteNumber;
    }
    public Long getOwnerId() {
        return this.ownerId;
    }
    public String getTitle() {
        return this.title;
    }
    public String getContents() {
        return this.contents;
    }
    public String getNoteType() {
        return this.noteType;
    }
    public String getColor() {
        return this.color;
    }
    public Boolean getIsPinned() {
        return this.isPinned;
    }
    public LocalDateTime getCreatedAt() {
        return this.createdAt;
    }
    public LocalDateTime getUpdatedAt() {
        return this.updatedAt;
    }
    public String getVisibility() {
        return this.visibility;
    }
    public void setNoteID(final Long noteID) {
        this.noteID = noteID;
    }
    public void setUserNoteNumber(final Integer userNoteNumber) {
        this.userNoteNumber = userNoteNumber;
    }
    public void setOwnerId(final Long ownerId) {
        this.ownerId = ownerId;
    }
    public void setTitle(final String title) {
        this.title = title;
    }
    public void setContents(final String contents) {
        this.contents = contents;
    }
    public void setNoteType(final String noteType) {
        this.noteType = noteType;
    }
    public void setColor(final String color) {
        this.color = color;
    }
    public void setIsPinned(final Boolean isPinned) {
        this.isPinned = isPinned;
    }
    public void setCreatedAt(final LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }
    public void setUpdatedAt(final LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }
    public void setVisibility(final String visibility) {
        this.visibility = visibility;
    }
    public NoteResponse() {
    }
    public NoteResponse(final Long noteID, final Integer userNoteNumber, final Long ownerId, final String title, final String contents, final String noteType, final String color, final Boolean isPinned, final LocalDateTime createdAt, final LocalDateTime updatedAt, final String visibility) {
        this.noteID = noteID;
        this.userNoteNumber = userNoteNumber;
        this.ownerId = ownerId;
        this.title = title;
        this.contents = contents;
        this.noteType = noteType;
        this.color = color;
        this.isPinned = isPinned;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.visibility = visibility;
    }
}
