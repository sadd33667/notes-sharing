package com.notes.sharing.dto;


public class FriendshipResponse {
    private Long relationshipID;
    private String friendUsername;
    private String status;
    public static class FriendshipResponseBuilder {
        private Long relationshipID;
        private String friendUsername;
        private String status;
        FriendshipResponseBuilder() {
        }

        /**
         * @return {@code this}.
         */
        public FriendshipResponse.FriendshipResponseBuilder relationshipID(final Long relationshipID) {
            this.relationshipID = relationshipID;
            return this;
        }

        /**
         * @return {@code this}.
         */
        public FriendshipResponse.FriendshipResponseBuilder friendUsername(final String friendUsername) {
            this.friendUsername = friendUsername;
            return this;
        }

        /**
         * @return {@code this}.
         */
        public FriendshipResponse.FriendshipResponseBuilder status(final String status) {
            this.status = status;
            return this;
        }
        public FriendshipResponse build() {
            return new FriendshipResponse(this.relationshipID, this.friendUsername, this.status);
        }

        @java.lang.Override
        public java.lang.String toString() {
            return "FriendshipResponse.FriendshipResponseBuilder(relationshipID=" + this.relationshipID + ", friendUsername=" + this.friendUsername + ", status=" + this.status + ")";
        }
    }
    public static FriendshipResponse.FriendshipResponseBuilder builder() {
        return new FriendshipResponse.FriendshipResponseBuilder();
    }
    public Long getRelationshipID() {
        return this.relationshipID;
    }
    public String getFriendUsername() {
        return this.friendUsername;
    }
    public String getStatus() {
        return this.status;
    }
    public void setRelationshipID(final Long relationshipID) {
        this.relationshipID = relationshipID;
    }
    public void setFriendUsername(final String friendUsername) {
        this.friendUsername = friendUsername;
    }
    public void setStatus(final String status) {
        this.status = status;
    }
    public FriendshipResponse() {
    }
    public FriendshipResponse(final Long relationshipID, final String friendUsername, final String status) {
        this.relationshipID = relationshipID;
        this.friendUsername = friendUsername;
        this.status = status;
    }
}
