package com.notes.sharing.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public class ChangePasswordRequest {
    @NotBlank
    private String oldPassword;
    @NotBlank
    @Size(min = 8, message = "Password must be at least 8 characters")
    private String newPassword;
    public static class ChangePasswordRequestBuilder {
        private String oldPassword;
        private String newPassword;
        ChangePasswordRequestBuilder() {
        }

        /**
         * @return {@code this}.
         */
        public ChangePasswordRequest.ChangePasswordRequestBuilder oldPassword(final String oldPassword) {
            this.oldPassword = oldPassword;
            return this;
        }

        /**
         * @return {@code this}.
         */
        public ChangePasswordRequest.ChangePasswordRequestBuilder newPassword(final String newPassword) {
            this.newPassword = newPassword;
            return this;
        }
        public ChangePasswordRequest build() {
            return new ChangePasswordRequest(this.oldPassword, this.newPassword);
        }

        @java.lang.Override
        public java.lang.String toString() {
            return "ChangePasswordRequest.ChangePasswordRequestBuilder(oldPassword=" + this.oldPassword + ", newPassword=" + this.newPassword + ")";
        }
    }
    public static ChangePasswordRequest.ChangePasswordRequestBuilder builder() {
        return new ChangePasswordRequest.ChangePasswordRequestBuilder();
    }
    public String getOldPassword() {
        return this.oldPassword;
    }
    public String getNewPassword() {
        return this.newPassword;
    }
    public void setOldPassword(final String oldPassword) {
        this.oldPassword = oldPassword;
    }
    public void setNewPassword(final String newPassword) {
        this.newPassword = newPassword;
    }
    public ChangePasswordRequest() {
    }
    public ChangePasswordRequest(final String oldPassword, final String newPassword) {
        this.oldPassword = oldPassword;
        this.newPassword = newPassword;
    }
}
