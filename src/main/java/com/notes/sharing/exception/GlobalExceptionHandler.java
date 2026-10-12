package com.notes.sharing.exception;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.Map;

// All my API errors look the same: { "error": "message" }
//
// When each one happens:
// - 404 NotFound: the thing really does not exist (bad user/note/request/notification
//   id, or login with unknown email). Same message for wrong password on purpose.
// - 403 Forbidden: it exists but it is not yours (private note, edit/delete/pin/share
//   without rights, answering or removing someone else's friendship, reading
//   someone else's notification, bad or missing token on protected routes).
// - 409 Conflict: duplicate email/username, weak password rules, bad permission or
//   visibility value, duplicate or already-decided friend request, sharing with
//   yourself, from-date after to-date.
// - 400 Bad Request: missing fields (validation) or bad calendar date format.
@RestControllerAdvice
public class GlobalExceptionHandler {

    // 404 - the thing really does not exist.
    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<Map<String, String>> notFound(NotFoundException e) {
        return ResponseEntity.status(404).body(Map.of("error", e.getMessage()));
    }

    // 409 - duplicate email/username, or action not allowed right now
    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<Map<String, String>> conflict(IllegalArgumentException e) {
        return ResponseEntity.status(409).body(Map.of("error", e.getMessage()));
    }

    // 403 - you are logged in, but this is not yours.
    @ExceptionHandler(ForbiddenException.class)
    public ResponseEntity<Map<String, String>> forbidden(ForbiddenException e) {
        return ResponseEntity.status(403).body(Map.of("error", e.getMessage()));
    }

    // 400 - wrong date format in calendar (?from&to= must be like 2026-01-01T00:00:00)
    @ExceptionHandler(java.time.format.DateTimeParseException.class)
    public ResponseEntity<Map<String, String>> badDate(java.time.format.DateTimeParseException e) {
        return ResponseEntity.status(400).body(Map.of("error", "Bad date, use like 2026-01-01T00:00:00"));
    }

    // 409 - any database clash (duplicate etc.)
    @ExceptionHandler(org.springframework.dao.DataIntegrityViolationException.class)
    public ResponseEntity<Map<String, String>> clash(org.springframework.dao.DataIntegrityViolationException e) {
        return ResponseEntity.status(409).body(Map.of("error", "Duplicate or conflicting data"));
    }
}
