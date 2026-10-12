package com.notes.sharing.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public class CreateNoteRequest {
    @NotNull
    private Long ownerId;
    @NotBlank
    private String title;
    private String contents;
    private String noteType;
    private String color;
    private String visibility;
    public static class CreateNoteRequestBuilder {
        private Long ownerId;
        private String title;
        private String contents;
        private String noteType;
        private String color;
        private String visibility;
        CreateNoteRequestBuilder() {
        }

        /**
         * @return {@code this}.
         */
        public CreateNoteRequest.CreateNoteRequestBuilder ownerId(final Long ownerId) {
            this.ownerId = ownerId;
            return this;
        }

        /**
         * @return {@code this}.
         */
        public CreateNoteRequest.CreateNoteRequestBuilder title(final String title) {
            this.title = title;
            return this;
        }

        /**
         * @return {@code this}.
         */
        public CreateNoteRequest.CreateNoteRequestBuilder contents(final String contents) {
            this.contents = contents;
            return this;
        }

        /**
         * @return {@code this}.
         */
        public CreateNoteRequest.CreateNoteRequestBuilder noteType(final String noteType) {
            this.noteType = noteType;
            return this;
        }

        /**
         * @return {@code this}.
         */
        public CreateNoteRequest.CreateNoteRequestBuilder color(final String color) {
            this.color = color;
            return this;
        }

        /**
         * @return {@code this}.
         */
        public CreateNoteRequest.CreateNoteRequestBuilder visibility(final String visibility) {
            this.visibility = visibility;
            return this;
        }
        public CreateNoteRequest build() {
            return new CreateNoteRequest(this.ownerId, this.title, this.contents, this.noteType, this.color, this.visibility);
        }

        @java.lang.Override
        public java.lang.String toString() {
            return "CreateNoteRequest.CreateNoteRequestBuilder(ownerId=" + this.ownerId + ", title=" + this.title + ", contents=" + this.contents + ", noteType=" + this.noteType + ", color=" + this.color + ", visibility=" + this.visibility + ")";
        }
    }
    public static CreateNoteRequest.CreateNoteRequestBuilder builder() {
        return new CreateNoteRequest.CreateNoteRequestBuilder();
    }
    public Long getOwnerId() {
        return this.ownerId;
    }
    public String getTitle() {
        return this.title;
    }
    public String getContents() {
        return this.contents;
    }
    public String getNoteType() {
        return this.noteType;
    }
    public String getColor() {
        return this.color;
    }
    public String getVisibility() {
        return this.visibility;
    }
    public void setOwnerId(final Long ownerId) {
        this.ownerId = ownerId;
    }
    public void setTitle(final String title) {
        this.title = title;
    }
    public void setContents(final String contents) {
        this.contents = contents;
    }
    public void setNoteType(final String noteType) {
        this.noteType = noteType;
    }
    public void setColor(final String color) {
        this.color = color;
    }
    public void setVisibility(final String visibility) {
        this.visibility = visibility;
    }
    public CreateNoteRequest() {
    }
    public CreateNoteRequest(final Long ownerId, final String title, final String contents, final String noteType, final String color, final String visibility) {
        this.ownerId = ownerId;
        this.title = title;
        this.contents = contents;
        this.noteType = noteType;
        this.color = color;
        this.visibility = visibility;
    }
}
