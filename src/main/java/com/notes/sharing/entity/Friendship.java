package com.notes.sharing.entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "friendships")
public class Friendship {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long relationshipID;
    @ManyToOne
    @JoinColumn(name = "fUserID", nullable = false)
    private User fUser;
    @ManyToOne
    @JoinColumn(name = "sUserID", nullable = false)
    private User sUser;
    private String status; // pending, accepted
    private LocalDateTime createdAt;

    @PrePersist
    public void prePersist() {
        if (createdAt == null) createdAt = LocalDateTime.now();
    }
    public static class FriendshipBuilder {
        private Long relationshipID;
        private User fUser;
        private User sUser;
        private String status;
        private LocalDateTime createdAt;
        FriendshipBuilder() {
        }

        /**
         * @return {@code this}.
         */
        public Friendship.FriendshipBuilder relationshipID(final Long relationshipID) {
            this.relationshipID = relationshipID;
            return this;
        }

        /**
         * @return {@code this}.
         */
        public Friendship.FriendshipBuilder fUser(final User fUser) {
            this.fUser = fUser;
            return this;
        }

        /**
         * @return {@code this}.
         */
        public Friendship.FriendshipBuilder sUser(final User sUser) {
            this.sUser = sUser;
            return this;
        }

        /**
         * @return {@code this}.
         */
        public Friendship.FriendshipBuilder status(final String status) {
            this.status = status;
            return this;
        }

        /**
         * @return {@code this}.
         */
        public Friendship.FriendshipBuilder createdAt(final LocalDateTime createdAt) {
            this.createdAt = createdAt;
            return this;
        }
        public Friendship build() {
            return new Friendship(this.relationshipID, this.fUser, this.sUser, this.status, this.createdAt);
        }

        @java.lang.Override
        public java.lang.String toString() {
            return "Friendship.FriendshipBuilder(relationshipID=" + this.relationshipID + ", fUser=" + this.fUser + ", sUser=" + this.sUser + ", status=" + this.status + ", createdAt=" + this.createdAt + ")";
        }
    }
    public static Friendship.FriendshipBuilder builder() {
        return new Friendship.FriendshipBuilder();
    }
    public Long getRelationshipID() {
        return this.relationshipID;
    }
    public User getFUser() {
        return this.fUser;
    }
    public User getSUser() {
        return this.sUser;
    }
    public String getStatus() {
        return this.status;
    }
    public LocalDateTime getCreatedAt() {
        return this.createdAt;
    }
    public void setRelationshipID(final Long relationshipID) {
        this.relationshipID = relationshipID;
    }
    public void setFUser(final User fUser) {
        this.fUser = fUser;
    }
    public void setSUser(final User sUser) {
        this.sUser = sUser;
    }
    public void setStatus(final String status) {
        this.status = status;
    }
    public void setCreatedAt(final LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }
    public Friendship() {
    }
    public Friendship(final Long relationshipID, final User fUser, final User sUser, final String status, final LocalDateTime createdAt) {
        this.relationshipID = relationshipID;
        this.fUser = fUser;
        this.sUser = sUser;
        this.status = status;
        this.createdAt = createdAt;
    }
}
