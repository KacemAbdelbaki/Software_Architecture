package com.soft.archi.software_architecture.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import java.util.NoSuchElementException;

@RestControllerAdvice
public class GlobalExceptionHandler {

    /**
     * Gestion des erreurs de validation (@Valid)
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, Object>> handleValidationExceptions(MethodArgumentNotValidException ex) {
        Map<String, Object> response = new HashMap<>();
        Map<String, String> errors = new HashMap<>();
        
        ex.getBindingResult().getAllErrors().forEach((error) -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            errors.put(fieldName, errorMessage);
        });
        
        response.put("timestamp", LocalDateTime.now());
        response.put("status", HttpStatus.BAD_REQUEST.value());
        response.put("error", "Erreur de validation");
        response.put("errors", errors);
        
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    /**
     * Gestion des entités non trouvées
     */
    @ExceptionHandler(NoSuchElementException.class)
    public ResponseEntity<Map<String, Object>> handleNotFoundException(NoSuchElementException ex) {
        Map<String, Object> response = new HashMap<>();
        
        response.put("timestamp", LocalDateTime.now());
        response.put("status", HttpStatus.NOT_FOUND.value());
        response.put("error", "Ressource non trouvée");
        response.put("message", "L'élément demandé n'existe pas dans la base de données");
        
        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    }

    /**
     * Gestion des erreurs de duplicate (unique constraint)
     */
    @ExceptionHandler(org.springframework.dao.DataIntegrityViolationException.class)
    public ResponseEntity<Map<String, Object>> handleDataIntegrityViolationException(
            org.springframework.dao.DataIntegrityViolationException ex) {
        Map<String, Object> response = new HashMap<>();
        
        String message = "Une entrée avec ces valeurs existe déjà";
        if (ex.getMessage().contains("username")) {
            message = "Ce nom d'utilisateur existe déjà";
        } else if (ex.getMessage().contains("email")) {
            message = "Cet email est déjà utilisé";
        } else if (ex.getMessage().contains("role")) {
            message = "Ce rôle existe déjà";
        }
        
        response.put("timestamp", LocalDateTime.now());
        response.put("status", HttpStatus.CONFLICT.value());
        response.put("error", "Conflit de données");
        response.put("message", message);
        
        return new ResponseEntity<>(response, HttpStatus.CONFLICT);
    }

    /**
     * Gestion des illegal argument exceptions
     */
    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<Map<String, Object>> handleIllegalArgumentException(IllegalArgumentException ex) {
        Map<String, Object> response = new HashMap<>();
        
        response.put("timestamp", LocalDateTime.now());
        response.put("status", HttpStatus.BAD_REQUEST.value());
        response.put("error", "Argument invalide");
        response.put("message", ex.getMessage());
        
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    /**
     * Gestion des exceptions génériques
     */
    @ExceptionHandler(Exception.class)
    public ResponseEntity<Map<String, Object>> handleGenericException(Exception ex) {
        Map<String, Object> response = new HashMap<>();
        
        response.put("timestamp", LocalDateTime.now());
        response.put("status", HttpStatus.INTERNAL_SERVER_ERROR.value());
        response.put("error", "Erreur serveur");
        response.put("message", "Une erreur inattendue s'est produite");
        
        // Log l'erreur pour le debug (devrait utiliser un logger en production)
        ex.printStackTrace();
        
        return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
