package com.notes.sharing.exception;

// My one exception type. Takes a code from the enum, a message,
// and the real cause when there is one (so I can log it).
public class SystemException extends RuntimeException {
    private final ErrorCode error;

    public SystemException(ErrorCode error) {
        super(error.getDefaultMessage());
        this.error = error;
    }

    public SystemException(ErrorCode error, String detail) {
        super(detail);
        this.error = error;
    }

    public SystemException(ErrorCode error, String detail, Throwable cause) {
        super(detail, cause);
        this.error = error;
    }

    public ErrorCode getError() {
        return error;
    }

    public int getCode() {
        return error.getCode();
    }

    public int httpStatus() {
        return error.httpStatus();
    }
}
