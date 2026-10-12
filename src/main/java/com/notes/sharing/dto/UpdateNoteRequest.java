package com.notes.sharing.dto;


public class UpdateNoteRequest {
    private String title;
    private String contents;
    public static class UpdateNoteRequestBuilder {
        private String title;
        private String contents;
        UpdateNoteRequestBuilder() {
        }

        /**
         * @return {@code this}.
         */
        public UpdateNoteRequest.UpdateNoteRequestBuilder title(final String title) {
            this.title = title;
            return this;
        }

        /**
         * @return {@code this}.
         */
        public UpdateNoteRequest.UpdateNoteRequestBuilder contents(final String contents) {
            this.contents = contents;
            return this;
        }
        public UpdateNoteRequest build() {
            return new UpdateNoteRequest(this.title, this.contents);
        }

        @java.lang.Override
        public java.lang.String toString() {
            return "UpdateNoteRequest.UpdateNoteRequestBuilder(title=" + this.title + ", contents=" + this.contents + ")";
        }
    }
    public static UpdateNoteRequest.UpdateNoteRequestBuilder builder() {
        return new UpdateNoteRequest.UpdateNoteRequestBuilder();
    }
    public String getTitle() {
        return this.title;
    }
    public String getContents() {
        return this.contents;
    }
    public void setTitle(final String title) {
        this.title = title;
    }
    public void setContents(final String contents) {
        this.contents = contents;
    }
    public UpdateNoteRequest() {
    }
    public UpdateNoteRequest(final String title, final String contents) {
        this.title = title;
        this.contents = contents;
    }
}
