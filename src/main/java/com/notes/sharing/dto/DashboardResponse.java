package com.notes.sharing.dto;


public class DashboardResponse {
    private long myNotesCount;
    private long sharedWithMeCount;
    private long friendsCount;
    public static class DashboardResponseBuilder {
        private long myNotesCount;
        private long sharedWithMeCount;
        private long friendsCount;
        DashboardResponseBuilder() {
        }

        /**
         * @return {@code this}.
         */
        public DashboardResponse.DashboardResponseBuilder myNotesCount(final long myNotesCount) {
            this.myNotesCount = myNotesCount;
            return this;
        }

        /**
         * @return {@code this}.
         */
        public DashboardResponse.DashboardResponseBuilder sharedWithMeCount(final long sharedWithMeCount) {
            this.sharedWithMeCount = sharedWithMeCount;
            return this;
        }

        /**
         * @return {@code this}.
         */
        public DashboardResponse.DashboardResponseBuilder friendsCount(final long friendsCount) {
            this.friendsCount = friendsCount;
            return this;
        }
        public DashboardResponse build() {
            return new DashboardResponse(this.myNotesCount, this.sharedWithMeCount, this.friendsCount);
        }

        @java.lang.Override
        public java.lang.String toString() {
            return "DashboardResponse.DashboardResponseBuilder(myNotesCount=" + this.myNotesCount + ", sharedWithMeCount=" + this.sharedWithMeCount + ", friendsCount=" + this.friendsCount + ")";
        }
    }
    public static DashboardResponse.DashboardResponseBuilder builder() {
        return new DashboardResponse.DashboardResponseBuilder();
    }
    public long getMyNotesCount() {
        return this.myNotesCount;
    }
    public long getSharedWithMeCount() {
        return this.sharedWithMeCount;
    }
    public long getFriendsCount() {
        return this.friendsCount;
    }
    public void setMyNotesCount(final long myNotesCount) {
        this.myNotesCount = myNotesCount;
    }
    public void setSharedWithMeCount(final long sharedWithMeCount) {
        this.sharedWithMeCount = sharedWithMeCount;
    }
    public void setFriendsCount(final long friendsCount) {
        this.friendsCount = friendsCount;
    }
    public DashboardResponse() {
    }
    public DashboardResponse(final long myNotesCount, final long sharedWithMeCount, final long friendsCount) {
        this.myNotesCount = myNotesCount;
        this.sharedWithMeCount = sharedWithMeCount;
        this.friendsCount = friendsCount;
    }
}
