package com.notes.sharing.dto;

import jakarta.validation.constraints.NotNull;

public class ShareRequest {
    @NotNull
    private Long userId;
    private String permission;
    public static class ShareRequestBuilder {
        private Long userId;
        private String permission;
        ShareRequestBuilder() {
        }

        /**
         * @return {@code this}.
         */
        public ShareRequest.ShareRequestBuilder userId(final Long userId) {
            this.userId = userId;
            return this;
        }

        /**
         * @return {@code this}.
         */
        public ShareRequest.ShareRequestBuilder permission(final String permission) {
            this.permission = permission;
            return this;
        }
        public ShareRequest build() {
            return new ShareRequest(this.userId, this.permission);
        }

        @java.lang.Override
        public java.lang.String toString() {
            return "ShareRequest.ShareRequestBuilder(userId=" + this.userId + ", permission=" + this.permission + ")";
        }
    }
    public static ShareRequest.ShareRequestBuilder builder() {
        return new ShareRequest.ShareRequestBuilder();
    }
    public Long getUserId() {
        return this.userId;
    }
    public String getPermission() {
        return this.permission;
    }
    public void setUserId(final Long userId) {
        this.userId = userId;
    }
    public void setPermission(final String permission) {
        this.permission = permission;
    }
    public ShareRequest() {
    }
    public ShareRequest(final Long userId, final String permission) {
        this.userId = userId;
        this.permission = permission;
    }
}
