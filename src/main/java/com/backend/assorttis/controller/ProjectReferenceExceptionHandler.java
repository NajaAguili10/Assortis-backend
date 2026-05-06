package com.backend.assorttis.controller;

import com.backend.assorttis.enums.ProjectNotFoundException;
import com.backend.assorttis.enums.ProjectReferenceGenerationException;
import com.backend.assorttis.enums.TemplateNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import java.util.Map;

@Slf4j
@RestControllerAdvice
public class ProjectReferenceExceptionHandler {

    @ExceptionHandler(ProjectNotFoundException.class)
    public ResponseEntity<Map<String, String>> handleProjectNotFound(ProjectNotFoundException exception) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Map.of("message", "Project not found"));
    }

    @ExceptionHandler({TemplateNotFoundException.class, MethodArgumentTypeMismatchException.class})
    public ResponseEntity<Map<String, String>> handleTemplateNotFound(Exception exception) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Map.of("message", "Template not found"));
    }

    @ExceptionHandler(ProjectReferenceGenerationException.class)
    public ResponseEntity<Map<String, String>> handleGenerationError(ProjectReferenceGenerationException exception) {
        log.error("Project reference generation failed", exception);
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Map.of("message", "Unable to generate reference"));
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, String>> handleValidation(MethodArgumentNotValidException exception) {
        return ResponseEntity.badRequest().body(Map.of("message", "Unable to generate reference"));
    }
}
