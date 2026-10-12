package com.notes.sharing.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public class LoginRequest {
    @Email
    @NotBlank
    private String email;
    @NotBlank
    private String password;
    public static class LoginRequestBuilder {
        private String email;
        private String password;
        LoginRequestBuilder() {
        }

        /**
         * @return {@code this}.
         */
        public LoginRequest.LoginRequestBuilder email(final String email) {
            this.email = email;
            return this;
        }

        /**
         * @return {@code this}.
         */
        public LoginRequest.LoginRequestBuilder password(final String password) {
            this.password = password;
            return this;
        }
        public LoginRequest build() {
            return new LoginRequest(this.email, this.password);
        }

        @java.lang.Override
        public java.lang.String toString() {
            return "LoginRequest.LoginRequestBuilder(email=" + this.email + ", password=" + this.password + ")";
        }
    }
    public static LoginRequest.LoginRequestBuilder builder() {
        return new LoginRequest.LoginRequestBuilder();
    }
    public String getEmail() {
        return this.email;
    }
    public String getPassword() {
        return this.password;
    }
    public void setEmail(final String email) {
        this.email = email;
    }
    public void setPassword(final String password) {
        this.password = password;
    }
    public LoginRequest() {
    }
    public LoginRequest(final String email, final String password) {
        this.email = email;
        this.password = password;
    }
}
