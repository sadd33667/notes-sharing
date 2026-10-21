package com.notes.sharing.exception;

// 404 with my own number, e.g. 1002 = note not found.
// 1001 user, 1002 note, 1003 friendship/request, 1004 notification, 1000 other.
public class NotFoundException extends RuntimeException {
    private final int code;

    public NotFoundException(String message) {
        this(1000, message);
    }

    public NotFoundException(int code, String message) {
        super(message);
        this.code = code;
    }

    public int getCode() {
        return code;
    }
}
