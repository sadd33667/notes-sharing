package com.notes.sharing.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public class RegisterRequest {
    @NotBlank
    private String username;
    @Email
    @NotBlank
    private String email;
    @NotBlank
    @Size(min = 8, message = "Password must be at least 8 characters")
    private String password;
    public static class RegisterRequestBuilder {
        private String username;
        private String email;
        private String password;
        RegisterRequestBuilder() {
        }

        /**
         * @return {@code this}.
         */
        public RegisterRequest.RegisterRequestBuilder username(final String username) {
            this.username = username;
            return this;
        }

        /**
         * @return {@code this}.
         */
        public RegisterRequest.RegisterRequestBuilder email(final String email) {
            this.email = email;
            return this;
        }

        /**
         * @return {@code this}.
         */
        public RegisterRequest.RegisterRequestBuilder password(final String password) {
            this.password = password;
            return this;
        }
        public RegisterRequest build() {
            return new RegisterRequest(this.username, this.email, this.password);
        }

        @java.lang.Override
        public java.lang.String toString() {
            return "RegisterRequest.RegisterRequestBuilder(username=" + this.username + ", email=" + this.email + ", password=" + this.password + ")";
        }
    }
    public static RegisterRequest.RegisterRequestBuilder builder() {
        return new RegisterRequest.RegisterRequestBuilder();
    }
    public String getUsername() {
        return this.username;
    }
    public String getEmail() {
        return this.email;
    }
    public String getPassword() {
        return this.password;
    }
    public void setUsername(final String username) {
        this.username = username;
    }
    public void setEmail(final String email) {
        this.email = email;
    }
    public void setPassword(final String password) {
        this.password = password;
    }
    public RegisterRequest() {
    }
    public RegisterRequest(final String username, final String email, final String password) {
        this.username = username;
        this.email = email;
        this.password = password;
    }
}
