package com.vaidhyaa.fraud.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, Object>> handleValidation(MethodArgumentNotValidException ex) {
        Map<String, Object> res = new HashMap<>();
        res.put("status", 400);
        res.put("error", "Validation Failed");
        res.put("message", ex.getBindingResult().getFieldError().getDefaultMessage());
        return ResponseEntity.badRequest().body(res);
    }

    @ExceptionHandler(DuplicateTransactionException.class)
    public ResponseEntity<Map<String, Object>> handleDuplicate(DuplicateTransactionException ex) {
        Map<String, Object> res = new HashMap<>();
        res.put("status", 409);
        res.put("error", "Conflict");
        res.put("message", ex.getMessage());
        return ResponseEntity.status(HttpStatus.CONFLICT).body(res);
    }

    // ✅ Bad JSON / invalid body
    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<Map<String, Object>> handleBadJson(HttpMessageNotReadableException ex) {
        Map<String, Object> res = new HashMap<>();
        res.put("status", 400);
        res.put("error", "Bad Request");
        res.put("message", "Invalid JSON request");
        return ResponseEntity.badRequest().body(res);
    }

    // ✅ Unsupported media type (text/plain etc.)
    @ExceptionHandler(HttpMediaTypeNotSupportedException.class)
    public ResponseEntity<Map<String, Object>> handleMediaType(HttpMediaTypeNotSupportedException ex) {
        Map<String, Object> res = new HashMap<>();
        res.put("status", 415);
        res.put("error", "Unsupported Media Type");
        res.put("message", ex.getMessage());
        return ResponseEntity.status(HttpStatus.UNSUPPORTED_MEDIA_TYPE).body(res);
    }

    // ✅ keep generic LAST
    @ExceptionHandler(Exception.class)
    public ResponseEntity<Map<String, Object>> handleGeneric(Exception ex) {
        Map<String, Object> res = new HashMap<>();
        res.put("status", 500);
        res.put("error", "Internal Server Error");
        res.put("message", ex.getMessage());
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(res);
    }
}