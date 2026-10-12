package com.notes.sharing.dto;

import jakarta.validation.constraints.NotBlank;

public class FriendRequest {
    @NotBlank
    private String username;
    public static class FriendRequestBuilder {
        private String username;
        FriendRequestBuilder() {
        }

        /**
         * @return {@code this}.
         */
        public FriendRequest.FriendRequestBuilder username(final String username) {
            this.username = username;
            return this;
        }
        public FriendRequest build() {
            return new FriendRequest(this.username);
        }

        @java.lang.Override
        public java.lang.String toString() {
            return "FriendRequest.FriendRequestBuilder(username=" + this.username + ")";
        }
    }
    public static FriendRequest.FriendRequestBuilder builder() {
        return new FriendRequest.FriendRequestBuilder();
    }
    public String getUsername() {
        return this.username;
    }
    public void setUsername(final String username) {
        this.username = username;
    }
    public FriendRequest() {
    }
    public FriendRequest(final String username) {
        this.username = username;
    }
}
