package com.notes.sharing.exception;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.Map;

// All my API errors look the same: { "error": "message" }
@RestControllerAdvice
public class GlobalExceptionHandler {

    // 404 - note/user/friend/request not found (also used to hide private stuff)
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
