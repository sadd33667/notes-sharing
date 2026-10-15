package com.notes.sharing.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

// My error numbers in one place. 1xxx missing, 2xxx not yours, 3xxx clash, 4000 bad input.
@Getter
@RequiredArgsConstructor
public enum ErrorCode {
    USER_NOT_FOUND(1001, "User not found"),
    NOTE_NOT_FOUND(1002, "Note not found"),
    FRIENDSHIP_NOT_FOUND(1003, "Request not found"),
    NOTIFICATION_NOT_FOUND(1004, "Notification not found"),
    INVALID_CREDENTIALS(1005, "Invalid email or password"),

    NOTE_PRIVATE(2001, "This note is private and not shared with you"),
    NOTE_EDIT_DENIED(2002, "Only the owner or an editor can change this note"),
    NOTE_DELETE_DENIED(2003, "Only the owner can delete this note"),
    NOTE_PIN_DENIED(2004, "Only the owner can pin this note"),
    NOTE_SHARE_DENIED(2005, "Only the owner can share this note"),
    FRIENDSHIP_DENIED(2006, "This friendship is not yours"),
    NOTIFICATION_DENIED(2007, "This notification is not yours"),

    EMAIL_TAKEN(3001, "Email already registered"),
    USERNAME_TAKEN(3002, "Username already taken"),
    BAD_PERMISSION(3003, "Permission must be view or edit"),
    BAD_VISIBILITY(3004, "Visibility must be private, public or shared"),
    SELF_ACTION(3005, "You cannot do this to yourself"),
    REQUEST_INVALID(3006, "Friend request is not valid anymore"),
    BAD_DATES(3007, "From date must be before to date"),
    WRONG_PASSWORD(3008, "Old password is wrong"),

    BAD_INPUT(4000, "Invalid input");

    private final int code;
    private final String defaultMessage;

    public int httpStatus() {
        return switch (code / 1000) {
            case 1 -> 404;
            case 2 -> 403;
            case 3 -> 409;
            default -> 400;
        };
    }
}
