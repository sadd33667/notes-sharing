package com.notes.sharing.dto;


public class AuthResponse {
    private String token;
    private Long userId;
    private String username;
    public static class AuthResponseBuilder {
        private String token;
        private Long userId;
        private String username;
        AuthResponseBuilder() {
        }

        /**
         * @return {@code this}.
         */
        public AuthResponse.AuthResponseBuilder token(final String token) {
            this.token = token;
            return this;
        }

        /**
         * @return {@code this}.
         */
        public AuthResponse.AuthResponseBuilder userId(final Long userId) {
            this.userId = userId;
            return this;
        }

        /**
         * @return {@code this}.
         */
        public AuthResponse.AuthResponseBuilder username(final String username) {
            this.username = username;
            return this;
        }
        public AuthResponse build() {
            return new AuthResponse(this.token, this.userId, this.username);
        }

        @java.lang.Override
        public java.lang.String toString() {
            return "AuthResponse.AuthResponseBuilder(token=" + this.token + ", userId=" + this.userId + ", username=" + this.username + ")";
        }
    }
    public static AuthResponse.AuthResponseBuilder builder() {
        return new AuthResponse.AuthResponseBuilder();
    }
    public String getToken() {
        return this.token;
    }
    public Long getUserId() {
        return this.userId;
    }
    public String getUsername() {
        return this.username;
    }
    public void setToken(final String token) {
        this.token = token;
    }
    public void setUserId(final Long userId) {
        this.userId = userId;
    }
    public void setUsername(final String username) {
        this.username = username;
    }
    public AuthResponse() {
    }
    public AuthResponse(final String token, final Long userId, final String username) {
        this.token = token;
        this.userId = userId;
        this.username = username;
    }
}
