package com.notes.sharing.entity;

import jakarta.persistence.*;
import com.fasterxml.jackson.annotation.JsonIgnore;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long userID;
    @Column(nullable = false, unique = true)
    private String username;
    private String userTag; // like sara#1234
    @Column(nullable = false, unique = true)
    private String email;
    @Column(nullable = false)
    private String passwordHash;
    @Column(length = 500)
    private String bio;
    private LocalDate birthDate;
    private String gender;
    private String phoneNumber;
    private String image;
    private String secondaryEmail;
    private LocalDateTime createdAt;
    // Relations - my vision
    @OneToOne(mappedBy = "user", cascade = CascadeType.ALL)
    @JsonIgnore
    private UserSettings settings;
    @OneToMany(mappedBy = "owner", cascade = CascadeType.ALL)
    @JsonIgnore
    private List<Note> notes;
    @OneToMany(mappedBy = "fUser")
    @JsonIgnore
    private List<Friendship> friendshipsSent;
    @OneToMany(mappedBy = "sUser")
    @JsonIgnore
    private List<Friendship> friendshipsReceived;
    @OneToMany(mappedBy = "user")
    @JsonIgnore
    private List<NoteSharing> myShares;

    @PrePersist
    public void prePersist() {
        if (createdAt == null) createdAt = LocalDateTime.now();
    }
    private static List<Note> $default$notes() {
        return new ArrayList<>();
    }
    private static List<Friendship> $default$friendshipsSent() {
        return new ArrayList<>();
    }
    private static List<Friendship> $default$friendshipsReceived() {
        return new ArrayList<>();
    }
    private static List<NoteSharing> $default$myShares() {
        return new ArrayList<>();
    }
    public static class UserBuilder {
        private Long userID;
        private String username;
        private String userTag;
        private String email;
        private String passwordHash;
        private String bio;
        private LocalDate birthDate;
        private String gender;
        private String phoneNumber;
        private String image;
        private String secondaryEmail;
        private LocalDateTime createdAt;
        private UserSettings settings;
        private boolean notes$set;
        private List<Note> notes$value;
        private boolean friendshipsSent$set;
        private List<Friendship> friendshipsSent$value;
        private boolean friendshipsReceived$set;
        private List<Friendship> friendshipsReceived$value;
        private boolean myShares$set;
        private List<NoteSharing> myShares$value;
        UserBuilder() {
        }

        /**
         * @return {@code this}.
         */
        public User.UserBuilder userID(final Long userID) {
            this.userID = userID;
            return this;
        }

        /**
         * @return {@code this}.
         */
        public User.UserBuilder username(final String username) {
            this.username = username;
            return this;
        }

        /**
         * @return {@code this}.
         */
        public User.UserBuilder userTag(final String userTag) {
            this.userTag = userTag;
            return this;
        }

        /**
         * @return {@code this}.
         */
        public User.UserBuilder email(final String email) {
            this.email = email;
            return this;
        }

        /**
         * @return {@code this}.
         */
        public User.UserBuilder passwordHash(final String passwordHash) {
            this.passwordHash = passwordHash;
            return this;
        }

        /**
         * @return {@code this}.
         */
        public User.UserBuilder bio(final String bio) {
            this.bio = bio;
            return this;
        }

        /**
         * @return {@code this}.
         */
        public User.UserBuilder birthDate(final LocalDate birthDate) {
            this.birthDate = birthDate;
            return this;
        }

        /**
         * @return {@code this}.
         */
        public User.UserBuilder gender(final String gender) {
            this.gender = gender;
            return this;
        }

        /**
         * @return {@code this}.
         */
        public User.UserBuilder phoneNumber(final String phoneNumber) {
            this.phoneNumber = phoneNumber;
            return this;
        }

        /**
         * @return {@code this}.
         */
        public User.UserBuilder image(final String image) {
            this.image = image;
            return this;
        }

        /**
         * @return {@code this}.
         */
        public User.UserBuilder secondaryEmail(final String secondaryEmail) {
            this.secondaryEmail = secondaryEmail;
            return this;
        }

        /**
         * @return {@code this}.
         */
        public User.UserBuilder createdAt(final LocalDateTime createdAt) {
            this.createdAt = createdAt;
            return this;
        }

        /**
         * @return {@code this}.
         */
        @JsonIgnore
        public User.UserBuilder settings(final UserSettings settings) {
            this.settings = settings;
            return this;
        }

        /**
         * @return {@code this}.
         */
        @JsonIgnore
        public User.UserBuilder notes(final List<Note> notes) {
            this.notes$value = notes;
            notes$set = true;
            return this;
        }

        /**
         * @return {@code this}.
         */
        @JsonIgnore
        public User.UserBuilder friendshipsSent(final List<Friendship> friendshipsSent) {
            this.friendshipsSent$value = friendshipsSent;
            friendshipsSent$set = true;
            return this;
        }

        /**
         * @return {@code this}.
         */
        @JsonIgnore
        public User.UserBuilder friendshipsReceived(final List<Friendship> friendshipsReceived) {
            this.friendshipsReceived$value = friendshipsReceived;
            friendshipsReceived$set = true;
            return this;
        }

        /**
         * @return {@code this}.
         */
        @JsonIgnore
        public User.UserBuilder myShares(final List<NoteSharing> myShares) {
            this.myShares$value = myShares;
            myShares$set = true;
            return this;
        }
        public User build() {
            List<Note> notes$value = this.notes$value;
            if (!this.notes$set) notes$value = User.$default$notes();
            List<Friendship> friendshipsSent$value = this.friendshipsSent$value;
            if (!this.friendshipsSent$set) friendshipsSent$value = User.$default$friendshipsSent();
            List<Friendship> friendshipsReceived$value = this.friendshipsReceived$value;
            if (!this.friendshipsReceived$set) friendshipsReceived$value = User.$default$friendshipsReceived();
            List<NoteSharing> myShares$value = this.myShares$value;
            if (!this.myShares$set) myShares$value = User.$default$myShares();
            return new User(this.userID, this.username, this.userTag, this.email, this.passwordHash, this.bio, this.birthDate, this.gender, this.phoneNumber, this.image, this.secondaryEmail, this.createdAt, this.settings, notes$value, friendshipsSent$value, friendshipsReceived$value, myShares$value);
        }

        @java.lang.Override
        public java.lang.String toString() {
            return "User.UserBuilder(userID=" + this.userID + ", username=" + this.username + ", userTag=" + this.userTag + ", email=" + this.email + ", passwordHash=" + this.passwordHash + ", bio=" + this.bio + ", birthDate=" + this.birthDate + ", gender=" + this.gender + ", phoneNumber=" + this.phoneNumber + ", image=" + this.image + ", secondaryEmail=" + this.secondaryEmail + ", createdAt=" + this.createdAt + ", settings=" + this.settings + ", notes$value=" + this.notes$value + ", friendshipsSent$value=" + this.friendshipsSent$value + ", friendshipsReceived$value=" + this.friendshipsReceived$value + ", myShares$value=" + this.myShares$value + ")";
        }
    }
    public static User.UserBuilder builder() {
        return new User.UserBuilder();
    }
    public Long getUserID() {
        return this.userID;
    }
    public String getUsername() {
        return this.username;
    }
    public String getUserTag() {
        return this.userTag;
    }
    public String getEmail() {
        return this.email;
    }
    public String getPasswordHash() {
        return this.passwordHash;
    }
    public String getBio() {
        return this.bio;
    }
    public LocalDate getBirthDate() {
        return this.birthDate;
    }
    public String getGender() {
        return this.gender;
    }
    public String getPhoneNumber() {
        return this.phoneNumber;
    }
    public String getImage() {
        return this.image;
    }
    public String getSecondaryEmail() {
        return this.secondaryEmail;
    }
    public LocalDateTime getCreatedAt() {
        return this.createdAt;
    }
    public UserSettings getSettings() {
        return this.settings;
    }
    public List<Note> getNotes() {
        return this.notes;
    }
    public List<Friendship> getFriendshipsSent() {
        return this.friendshipsSent;
    }
    public List<Friendship> getFriendshipsReceived() {
        return this.friendshipsReceived;
    }
    public List<NoteSharing> getMyShares() {
        return this.myShares;
    }
    public void setUserID(final Long userID) {
        this.userID = userID;
    }
    public void setUsername(final String username) {
        this.username = username;
    }
    public void setUserTag(final String userTag) {
        this.userTag = userTag;
    }
    public void setEmail(final String email) {
        this.email = email;
    }
    public void setPasswordHash(final String passwordHash) {
        this.passwordHash = passwordHash;
    }
    public void setBio(final String bio) {
        this.bio = bio;
    }
    public void setBirthDate(final LocalDate birthDate) {
        this.birthDate = birthDate;
    }
    public void setGender(final String gender) {
        this.gender = gender;
    }
    public void setPhoneNumber(final String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }
    public void setImage(final String image) {
        this.image = image;
    }
    public void setSecondaryEmail(final String secondaryEmail) {
        this.secondaryEmail = secondaryEmail;
    }
    public void setCreatedAt(final LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }
    public void setSettings(final UserSettings settings) {
        this.settings = settings;
    }
    public void setNotes(final List<Note> notes) {
        this.notes = notes;
    }
    public void setFriendshipsSent(final List<Friendship> friendshipsSent) {
        this.friendshipsSent = friendshipsSent;
    }
    public void setFriendshipsReceived(final List<Friendship> friendshipsReceived) {
        this.friendshipsReceived = friendshipsReceived;
    }
    public void setMyShares(final List<NoteSharing> myShares) {
        this.myShares = myShares;
    }
    public User() {
        this.notes = User.$default$notes();
        this.friendshipsSent = User.$default$friendshipsSent();
        this.friendshipsReceived = User.$default$friendshipsReceived();
        this.myShares = User.$default$myShares();
    }
    public User(final Long userID, final String username, final String userTag, final String email, final String passwordHash, final String bio, final LocalDate birthDate, final String gender, final String phoneNumber, final String image, final String secondaryEmail, final LocalDateTime createdAt, final UserSettings settings, final List<Note> notes, final List<Friendship> friendshipsSent, final List<Friendship> friendshipsReceived, final List<NoteSharing> myShares) {
        this.userID = userID;
        this.username = username;
        this.userTag = userTag;
        this.email = email;
        this.passwordHash = passwordHash;
        this.bio = bio;
        this.birthDate = birthDate;
        this.gender = gender;
        this.phoneNumber = phoneNumber;
        this.image = image;
        this.secondaryEmail = secondaryEmail;
        this.createdAt = createdAt;
        this.settings = settings;
        this.notes = notes;
        this.friendshipsSent = friendshipsSent;
        this.friendshipsReceived = friendshipsReceived;
        this.myShares = myShares;
    }
}
