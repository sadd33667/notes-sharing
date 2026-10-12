package com.notes.sharing.exception;

// 403 - you are logged in, but this is not yours to see or touch.
public class ForbiddenException extends RuntimeException {
    public ForbiddenException(String message) {
        super(message);
    }
}
