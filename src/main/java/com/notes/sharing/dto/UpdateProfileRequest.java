package com.notes.sharing.dto;


public class UpdateProfileRequest {
    private String bio;
    private String phoneNumber;
    private String image;
    private String secondaryEmail;
    public static class UpdateProfileRequestBuilder {
        private String bio;
        private String phoneNumber;
        private String image;
        private String secondaryEmail;
        UpdateProfileRequestBuilder() {
        }

        /**
         * @return {@code this}.
         */
        public UpdateProfileRequest.UpdateProfileRequestBuilder bio(final String bio) {
            this.bio = bio;
            return this;
        }

        /**
         * @return {@code this}.
         */
        public UpdateProfileRequest.UpdateProfileRequestBuilder phoneNumber(final String phoneNumber) {
            this.phoneNumber = phoneNumber;
            return this;
        }

        /**
         * @return {@code this}.
         */
        public UpdateProfileRequest.UpdateProfileRequestBuilder image(final String image) {
            this.image = image;
            return this;
        }

        /**
         * @return {@code this}.
         */
        public UpdateProfileRequest.UpdateProfileRequestBuilder secondaryEmail(final String secondaryEmail) {
            this.secondaryEmail = secondaryEmail;
            return this;
        }
        public UpdateProfileRequest build() {
            return new UpdateProfileRequest(this.bio, this.phoneNumber, this.image, this.secondaryEmail);
        }

        @java.lang.Override
        public java.lang.String toString() {
            return "UpdateProfileRequest.UpdateProfileRequestBuilder(bio=" + this.bio + ", phoneNumber=" + this.phoneNumber + ", image=" + this.image + ", secondaryEmail=" + this.secondaryEmail + ")";
        }
    }
    public static UpdateProfileRequest.UpdateProfileRequestBuilder builder() {
        return new UpdateProfileRequest.UpdateProfileRequestBuilder();
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
    public UpdateProfileRequest() {
    }
    public UpdateProfileRequest(final String bio, final String phoneNumber, final String image, final String secondaryEmail) {
        this.bio = bio;
        this.phoneNumber = phoneNumber;
        this.image = image;
        this.secondaryEmail = secondaryEmail;
    }
}
