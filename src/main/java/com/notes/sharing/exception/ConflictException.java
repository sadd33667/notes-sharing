package com.notes.sharing.exception;

// 409 with my own number, e.g. 3001 = email taken.
// 3001 email, 3002 username, 3003 permission, 3004 visibility, 3005 self action,
// 3006 duplicate/decided request, 3007 bad dates.
public class ConflictException extends RuntimeException {
    private final int code;

    public ConflictException(String message) {
        this(3000, message);
    }

    public ConflictException(int code, String message) {
        super(message);
        this.code = code;
    }

    public int getCode() {
        return code;
    }
}
