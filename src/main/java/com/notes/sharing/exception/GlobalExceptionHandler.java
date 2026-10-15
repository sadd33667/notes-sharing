package com.notes.sharing.exception;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.Map;

// All my API errors look the same: { "code": 2001, "error": "..." }.
// The number comes from my ErrorCode enum, one exception type for everything.
// I log the code with its cause here so errors leave a trace in the logs.
@RestControllerAdvice
public class GlobalExceptionHandler {

    private final Logger log = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    // My own errors: status comes from the code family (1xxx->404, 2xxx->403, 3xxx->409, 4000->400).
    @ExceptionHandler(SystemException.class)
    public ResponseEntity<Map<String, Object>> system(SystemException e) {
        if (e.getCause() != null) {
            log.warn("code={} error={}", e.getCode(), e.getMessage(), e.getCause());
        } else {
            log.info("code={} error={}", e.getCode(), e.getMessage());
        }
        return ResponseEntity.status(e.httpStatus())
                .body(Map.of("code", e.getCode(), "error", e.getMessage()));
    }

    // 400 - wrong date format in calendar (?from&to= must be like 2026-01-01T00:00:00)
    @ExceptionHandler(java.time.format.DateTimeParseException.class)
    public ResponseEntity<Map<String, Object>> badDate(java.time.format.DateTimeParseException e) {
        log.info("code=4000 error=Bad date");
        return ResponseEntity.status(400).body(Map.of("code", 4000, "error", "Bad date, use like 2026-01-01T00:00:00"));
    }

    // 400 - missing or bad fields. First problem only, kept short for the user.
    @ExceptionHandler(org.springframework.web.bind.MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, Object>> badInput(org.springframework.web.bind.MethodArgumentNotValidException e) {
        String message = e.getBindingResult().getFieldErrors().stream()
                .findFirst()
                .map(f -> f.getField() + " " + f.getDefaultMessage())
                .orElse("Invalid input");
        log.info("code=4000 error={}", message);
        return ResponseEntity.status(400).body(Map.of("code", 4000, "error", message));
    }

    // 409 - any database clash (duplicate etc.)
    @ExceptionHandler(org.springframework.dao.DataIntegrityViolationException.class)
    public ResponseEntity<Map<String, Object>> clash(org.springframework.dao.DataIntegrityViolationException e) {
        log.warn("code=3000 error=Duplicate data", e);
        return ResponseEntity.status(409).body(Map.of("code", 3000, "error", "Duplicate or conflicting data"));
    }
}
