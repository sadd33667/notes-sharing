package com.notes.sharing.exception;

// 403 with my own number, e.g. 2001 = private note.
// 2001 private note, 2002 edit, 2003 delete, 2004 pin, 2005 share, 2006 friendship, 2007 notification.
public class ForbiddenException extends RuntimeException {
    private final int code;

    public ForbiddenException(String message) {
        this(2000, message);
    }

    public ForbiddenException(int code, String message) {
        super(message);
        this.code = code;
    }

    public int getCode() {
        return code;
    }
}
