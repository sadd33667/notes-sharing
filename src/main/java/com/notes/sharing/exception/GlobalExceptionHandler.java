package com.notes.sharing.exception;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.Map;

// All my API errors look the same: { "code": 1001, "error": "message" }
//
// My own numbers (besides the HTTP status):
// - 1xxx not found: 1001 user, 1002 note, 1003 friendship/request, 1004 notification.
// - 2xxx forbidden: 2001 private note, 2002 edit, 2003 delete, 2004 pin,
//   2005 share, 2006 friendship, 2007 notification.
// - 3xxx conflict: 3001 email, 3002 username, 3003 permission, 3004 visibility,
//   3005 self action, 3006 duplicate/decided request, 3007 bad dates.
// - 4000 bad input: missing fields or bad calendar date format.
@RestControllerAdvice
public class GlobalExceptionHandler {

    // 404 - the thing really does not exist.
    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<Map<String, Object>> notFound(NotFoundException e) {
        return ResponseEntity.status(404).body(Map.of("code", e.getCode(), "error", e.getMessage()));
    }

    // 403 - you are logged in, but this is not yours.
    @ExceptionHandler(ForbiddenException.class)
    public ResponseEntity<Map<String, Object>> forbidden(ForbiddenException e) {
        return ResponseEntity.status(403).body(Map.of("code", e.getCode(), "error", e.getMessage()));
    }

    // 409 - duplicate or not allowed right now.
    @ExceptionHandler(ConflictException.class)
    public ResponseEntity<Map<String, Object>> conflict(ConflictException e) {
        return ResponseEntity.status(409).body(Map.of("code", e.getCode(), "error", e.getMessage()));
    }

    // 400 - wrong date format in calendar (?from&to= must be like 2026-01-01T00:00:00)
    @ExceptionHandler(java.time.format.DateTimeParseException.class)
    public ResponseEntity<Map<String, Object>> badDate(java.time.format.DateTimeParseException e) {
        return ResponseEntity.status(400).body(Map.of("code", 4000, "error", "Bad date, use like 2026-01-01T00:00:00"));
    }

    // 409 - any database clash (duplicate etc.)
    @ExceptionHandler(org.springframework.dao.DataIntegrityViolationException.class)
    public ResponseEntity<Map<String, Object>> clash(org.springframework.dao.DataIntegrityViolationException e) {
        return ResponseEntity.status(409).body(Map.of("code", 3000, "error", "Duplicate or conflicting data"));
    }
}
