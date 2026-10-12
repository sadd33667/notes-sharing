package com.notes.sharing.entity;

import jakarta.persistence.*;
import com.fasterxml.jackson.annotation.JsonIgnore;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "notes")
public class Note {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long noteID;
    @ManyToOne
    @JoinColumn(name = "ownerID", nullable = false)
    private User owner;
    private String title;
    @Column(length = 5000)
    private String contents;
    // My own number inside my notes: 1, 2, 3... (noteID stays the global key).
    private Integer userNoteNumber;
    private String noteType; // text, checklist, image
    private String color;
    private Boolean isPinned;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private LocalDateTime startDate;
    private LocalDateTime endDate;
    private Integer notifyThreshold;
    private String visibility; // private, public, shared
    // Relations
    @OneToMany(mappedBy = "note", cascade = CascadeType.ALL)
    @JsonIgnore
    private List<NoteSharing> sharedWith;

    @PrePersist
    public void prePersist() {
        if (createdAt == null) createdAt = LocalDateTime.now();
        updatedAt = LocalDateTime.now();
    }

    @PreUpdate
    public void preUpdate() {
        updatedAt = LocalDateTime.now();
    }
    private static Boolean $default$isPinned() {
        return false;
    }
    private static List<NoteSharing> $default$sharedWith() {
        return new ArrayList<>();
    }
    public static class NoteBuilder {
        private Long noteID;
        private User owner;
        private String title;
        private String contents;
        private Integer userNoteNumber;
        private String noteType;
        private String color;
        private boolean isPinned$set;
        private Boolean isPinned$value;
        private LocalDateTime createdAt;
        private LocalDateTime updatedAt;
        private LocalDateTime startDate;
        private LocalDateTime endDate;
        private Integer notifyThreshold;
        private String visibility;
        private boolean sharedWith$set;
        private List<NoteSharing> sharedWith$value;
        NoteBuilder() {
        }

        /**
         * @return {@code this}.
         */
        public Note.NoteBuilder noteID(final Long noteID) {
            this.noteID = noteID;
            return this;
        }

        /**
         * @return {@code this}.
         */
        public Note.NoteBuilder owner(final User owner) {
            this.owner = owner;
            return this;
        }

        /**
         * @return {@code this}.
         */
        public Note.NoteBuilder title(final String title) {
            this.title = title;
            return this;
        }

        /**
         * @return {@code this}.
         */
        public Note.NoteBuilder contents(final String contents) {
            this.contents = contents;
            return this;
        }

        /**
         * @return {@code this}.
         */
        public Note.NoteBuilder userNoteNumber(final Integer userNoteNumber) {
            this.userNoteNumber = userNoteNumber;
            return this;
        }

        /**
         * @return {@code this}.
         */
        public Note.NoteBuilder noteType(final String noteType) {
            this.noteType = noteType;
            return this;
        }

        /**
         * @return {@code this}.
         */
        public Note.NoteBuilder color(final String color) {
            this.color = color;
            return this;
        }

        /**
         * @return {@code this}.
         */
        public Note.NoteBuilder isPinned(final Boolean isPinned) {
            this.isPinned$value = isPinned;
            isPinned$set = true;
            return this;
        }

        /**
         * @return {@code this}.
         */
        public Note.NoteBuilder createdAt(final LocalDateTime createdAt) {
            this.createdAt = createdAt;
            return this;
        }

        /**
         * @return {@code this}.
         */
        public Note.NoteBuilder updatedAt(final LocalDateTime updatedAt) {
            this.updatedAt = updatedAt;
            return this;
        }

        /**
         * @return {@code this}.
         */
        public Note.NoteBuilder startDate(final LocalDateTime startDate) {
            this.startDate = startDate;
            return this;
        }

        /**
         * @return {@code this}.
         */
        public Note.NoteBuilder endDate(final LocalDateTime endDate) {
            this.endDate = endDate;
            return this;
        }

        /**
         * @return {@code this}.
         */
        public Note.NoteBuilder notifyThreshold(final Integer notifyThreshold) {
            this.notifyThreshold = notifyThreshold;
            return this;
        }

        /**
         * @return {@code this}.
         */
        public Note.NoteBuilder visibility(final String visibility) {
            this.visibility = visibility;
            return this;
        }

        /**
         * @return {@code this}.
         */
        @JsonIgnore
        public Note.NoteBuilder sharedWith(final List<NoteSharing> sharedWith) {
            this.sharedWith$value = sharedWith;
            sharedWith$set = true;
            return this;
        }
        public Note build() {
            Boolean isPinned$value = this.isPinned$value;
            if (!this.isPinned$set) isPinned$value = Note.$default$isPinned();
            List<NoteSharing> sharedWith$value = this.sharedWith$value;
            if (!this.sharedWith$set) sharedWith$value = Note.$default$sharedWith();
            return new Note(this.noteID, this.owner, this.title, this.contents, this.userNoteNumber, this.noteType, this.color, isPinned$value, this.createdAt, this.updatedAt, this.startDate, this.endDate, this.notifyThreshold, this.visibility, sharedWith$value);
        }

        @java.lang.Override
        public java.lang.String toString() {
            return "Note.NoteBuilder(noteID=" + this.noteID + ", owner=" + this.owner + ", title=" + this.title + ", contents=" + this.contents + ", userNoteNumber=" + this.userNoteNumber + ", noteType=" + this.noteType + ", color=" + this.color + ", isPinned$value=" + this.isPinned$value + ", createdAt=" + this.createdAt + ", updatedAt=" + this.updatedAt + ", startDate=" + this.startDate + ", endDate=" + this.endDate + ", notifyThreshold=" + this.notifyThreshold + ", visibility=" + this.visibility + ", sharedWith$value=" + this.sharedWith$value + ")";
        }
    }
    public static Note.NoteBuilder builder() {
        return new Note.NoteBuilder();
    }
    public Long getNoteID() {
        return this.noteID;
    }
    public User getOwner() {
        return this.owner;
    }
    public String getTitle() {
        return this.title;
    }
    public String getContents() {
        return this.contents;
    }
    public Integer getUserNoteNumber() {
        return this.userNoteNumber;
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
    public LocalDateTime getStartDate() {
        return this.startDate;
    }
    public LocalDateTime getEndDate() {
        return this.endDate;
    }
    public Integer getNotifyThreshold() {
        return this.notifyThreshold;
    }
    public String getVisibility() {
        return this.visibility;
    }
    public List<NoteSharing> getSharedWith() {
        return this.sharedWith;
    }
    public void setNoteID(final Long noteID) {
        this.noteID = noteID;
    }
    public void setOwner(final User owner) {
        this.owner = owner;
    }
    public void setTitle(final String title) {
        this.title = title;
    }
    public void setContents(final String contents) {
        this.contents = contents;
    }
    public void setUserNoteNumber(final Integer userNoteNumber) {
        this.userNoteNumber = userNoteNumber;
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
    public void setStartDate(final LocalDateTime startDate) {
        this.startDate = startDate;
    }
    public void setEndDate(final LocalDateTime endDate) {
        this.endDate = endDate;
    }
    public void setNotifyThreshold(final Integer notifyThreshold) {
        this.notifyThreshold = notifyThreshold;
    }
    public void setVisibility(final String visibility) {
        this.visibility = visibility;
    }
    public void setSharedWith(final List<NoteSharing> sharedWith) {
        this.sharedWith = sharedWith;
    }
    public Note() {
        this.isPinned = Note.$default$isPinned();
        this.sharedWith = Note.$default$sharedWith();
    }
    public Note(final Long noteID, final User owner, final String title, final String contents, final Integer userNoteNumber, final String noteType, final String color, final Boolean isPinned, final LocalDateTime createdAt, final LocalDateTime updatedAt, final LocalDateTime startDate, final LocalDateTime endDate, final Integer notifyThreshold, final String visibility, final List<NoteSharing> sharedWith) {
        this.noteID = noteID;
        this.owner = owner;
        this.title = title;
        this.contents = contents;
        this.userNoteNumber = userNoteNumber;
        this.noteType = noteType;
        this.color = color;
        this.isPinned = isPinned;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.startDate = startDate;
        this.endDate = endDate;
        this.notifyThreshold = notifyThreshold;
        this.visibility = visibility;
        this.sharedWith = sharedWith;
    }
}
