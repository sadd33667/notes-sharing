package com.notes.sharing.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "note_sharing")
public class NoteSharing {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long sharingID;
    @ManyToOne
    @JoinColumn(name = "userID", nullable = false)
    private User user;
    @ManyToOne
    @JoinColumn(name = "noteID", nullable = false)
    private Note note;
    private String permission; // view, edit
    private Boolean isFavorite;
    private static Boolean $default$isFavorite() {
        return false;
    }
    public static class NoteSharingBuilder {
        private Long sharingID;
        private User user;
        private Note note;
        private String permission;
        private boolean isFavorite$set;
        private Boolean isFavorite$value;
        NoteSharingBuilder() {
        }

        /**
         * @return {@code this}.
         */
        public NoteSharing.NoteSharingBuilder sharingID(final Long sharingID) {
            this.sharingID = sharingID;
            return this;
        }

        /**
         * @return {@code this}.
         */
        public NoteSharing.NoteSharingBuilder user(final User user) {
            this.user = user;
            return this;
        }

        /**
         * @return {@code this}.
         */
        public NoteSharing.NoteSharingBuilder note(final Note note) {
            this.note = note;
            return this;
        }

        /**
         * @return {@code this}.
         */
        public NoteSharing.NoteSharingBuilder permission(final String permission) {
            this.permission = permission;
            return this;
        }

        /**
         * @return {@code this}.
         */
        public NoteSharing.NoteSharingBuilder isFavorite(final Boolean isFavorite) {
            this.isFavorite$value = isFavorite;
            isFavorite$set = true;
            return this;
        }
        public NoteSharing build() {
            Boolean isFavorite$value = this.isFavorite$value;
            if (!this.isFavorite$set) isFavorite$value = NoteSharing.$default$isFavorite();
            return new NoteSharing(this.sharingID, this.user, this.note, this.permission, isFavorite$value);
        }

        @java.lang.Override
        public java.lang.String toString() {
            return "NoteSharing.NoteSharingBuilder(sharingID=" + this.sharingID + ", user=" + this.user + ", note=" + this.note + ", permission=" + this.permission + ", isFavorite$value=" + this.isFavorite$value + ")";
        }
    }
    public static NoteSharing.NoteSharingBuilder builder() {
        return new NoteSharing.NoteSharingBuilder();
    }
    public Long getSharingID() {
        return this.sharingID;
    }
    public User getUser() {
        return this.user;
    }
    public Note getNote() {
        return this.note;
    }
    public String getPermission() {
        return this.permission;
    }
    public Boolean getIsFavorite() {
        return this.isFavorite;
    }
    public void setSharingID(final Long sharingID) {
        this.sharingID = sharingID;
    }
    public void setUser(final User user) {
        this.user = user;
    }
    public void setNote(final Note note) {
        this.note = note;
    }
    public void setPermission(final String permission) {
        this.permission = permission;
    }
    public void setIsFavorite(final Boolean isFavorite) {
        this.isFavorite = isFavorite;
    }
    public NoteSharing() {
        this.isFavorite = NoteSharing.$default$isFavorite();
    }
    public NoteSharing(final Long sharingID, final User user, final Note note, final String permission, final Boolean isFavorite) {
        this.sharingID = sharingID;
        this.user = user;
        this.note = note;
        this.permission = permission;
        this.isFavorite = isFavorite;
    }
}
