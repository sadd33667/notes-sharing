package com.notes.sharing.dto;


public class ProfileResponse {
    private Long userID;
    private String username;
    private String userTag;
    private String email;
    private String bio;
    private String phoneNumber;
    private String image;
    private String secondaryEmail;
    public static class ProfileResponseBuilder {
        private Long userID;
        private String username;
        private String userTag;
        private String email;
        private String bio;
        private String phoneNumber;
        private String image;
        private String secondaryEmail;
        ProfileResponseBuilder() {
        }

        /**
         * @return {@code this}.
         */
        public ProfileResponse.ProfileResponseBuilder userID(final Long userID) {
            this.userID = userID;
            return this;
        }

        /**
         * @return {@code this}.
         */
        public ProfileResponse.ProfileResponseBuilder username(final String username) {
            this.username = username;
            return this;
        }

        /**
         * @return {@code this}.
         */
        public ProfileResponse.ProfileResponseBuilder userTag(final String userTag) {
            this.userTag = userTag;
            return this;
        }

        /**
         * @return {@code this}.
         */
        public ProfileResponse.ProfileResponseBuilder email(final String email) {
            this.email = email;
            return this;
        }

        /**
         * @return {@code this}.
         */
        public ProfileResponse.ProfileResponseBuilder bio(final String bio) {
            this.bio = bio;
            return this;
        }

        /**
         * @return {@code this}.
         */
        public ProfileResponse.ProfileResponseBuilder phoneNumber(final String phoneNumber) {
            this.phoneNumber = phoneNumber;
            return this;
        }

        /**
         * @return {@code this}.
         */
        public ProfileResponse.ProfileResponseBuilder image(final String image) {
            this.image = image;
            return this;
        }

        /**
         * @return {@code this}.
         */
        public ProfileResponse.ProfileResponseBuilder secondaryEmail(final String secondaryEmail) {
            this.secondaryEmail = secondaryEmail;
            return this;
        }
        public ProfileResponse build() {
            return new ProfileResponse(this.userID, this.username, this.userTag, this.email, this.bio, this.phoneNumber, this.image, this.secondaryEmail);
        }

        @java.lang.Override
        public java.lang.String toString() {
            return "ProfileResponse.ProfileResponseBuilder(userID=" + this.userID + ", username=" + this.username + ", userTag=" + this.userTag + ", email=" + this.email + ", bio=" + this.bio + ", phoneNumber=" + this.phoneNumber + ", image=" + this.image + ", secondaryEmail=" + this.secondaryEmail + ")";
        }
    }
    public static ProfileResponse.ProfileResponseBuilder builder() {
        return new ProfileResponse.ProfileResponseBuilder();
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
    public String getBio() {
        return this.bio;
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
    public void setBio(final String bio) {
        this.bio = bio;
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
    public ProfileResponse() {
    }
    public ProfileResponse(final Long userID, final String username, final String userTag, final String email, final String bio, final String phoneNumber, final String image, final String secondaryEmail) {
        this.userID = userID;
        this.username = username;
        this.userTag = userTag;
        this.email = email;
        this.bio = bio;
        this.phoneNumber = phoneNumber;
        this.image = image;
        this.secondaryEmail = secondaryEmail;
    }
}
