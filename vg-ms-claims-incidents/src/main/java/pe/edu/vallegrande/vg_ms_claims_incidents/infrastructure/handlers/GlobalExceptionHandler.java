package pe.edu.vallegrande.vg_ms_claims_incidents.infrastructure.handlers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.bind.support.WebExchangeBindException;
import pe.edu.vallegrande.vg_ms_claims_incidents.infrastructure.exception.DatosInvalidosException;
import pe.edu.vallegrande.vg_ms_claims_incidents.infrastructure.exception.ErrorServidorException;
import pe.edu.vallegrande.vg_ms_claims_incidents.infrastructure.exception.RecursoNoEncontradoException;
import lombok.extern.slf4j.Slf4j;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(RecursoNoEncontradoException.class)
    public ResponseEntity<Map<String, Object>> handleRecursoNoEncontrado(RecursoNoEncontradoException ex) {
        log.error("Recurso no encontrado: {}", ex.getMessage(), ex);
        Map<String, Object> respuesta = new HashMap<>();
        respuesta.put("timestamp", LocalDateTime.now());
        respuesta.put("status", HttpStatus.NOT_FOUND.value());
        respuesta.put("error", "Recurso no encontrado");
        respuesta.put("mensaje", ex.getMessage());
        respuesta.put("path", "/api/v1");

        return new ResponseEntity<>(respuesta, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(DatosInvalidosException.class)
    public ResponseEntity<Map<String, Object>> handleDatosInvalidos(DatosInvalidosException ex) {
        log.error("Datos inválidos: {}", ex.getMessage(), ex);
        Map<String, Object> respuesta = new HashMap<>();
        respuesta.put("timestamp", LocalDateTime.now());
        respuesta.put("status", HttpStatus.BAD_REQUEST.value());
        respuesta.put("error", "Datos inválidos");
        respuesta.put("mensaje", ex.getMessage());
        respuesta.put("path", "/api/v1");

        return new ResponseEntity<>(respuesta, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(ErrorServidorException.class)
    public ResponseEntity<Map<String, Object>> handleErrorServidor(ErrorServidorException ex) {
        log.error("Error interno del servidor: {}", ex.getMessage(), ex);
        Map<String, Object> respuesta = new HashMap<>();
        respuesta.put("timestamp", LocalDateTime.now());
        respuesta.put("status", HttpStatus.INTERNAL_SERVER_ERROR.value());
        respuesta.put("error", "Error interno del servidor");
        respuesta.put("mensaje", ex.getMessage());
        respuesta.put("path", "/api/v1");

        return new ResponseEntity<>(respuesta, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(WebExchangeBindException.class)
    public ResponseEntity<Map<String, Object>> handleValidationExceptions(WebExchangeBindException ex) {
        log.error("Error de validación: {}", ex.getMessage(), ex);
        Map<String, Object> respuesta = new HashMap<>();
        Map<String, String> errores = new HashMap<>();

        ex.getBindingResult().getFieldErrors()
                .forEach(error -> errores.put(error.getField(), error.getDefaultMessage()));

        respuesta.put("timestamp", LocalDateTime.now());
        respuesta.put("status", HttpStatus.BAD_REQUEST.value());
        respuesta.put("error", "Error de validación");
        respuesta.put("mensaje", "Los datos proporcionados no son válidos");
        respuesta.put("errores", errores);
        respuesta.put("path", "/api/v1");

        return new ResponseEntity<>(respuesta, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<Map<String, Object>> handleHttpMessageNotReadable(HttpMessageNotReadableException ex) {
        log.error("Error al leer el mensaje HTTP: {}", ex.getMessage(), ex);

        // Obtener más detalles del error
        String errorDetails = ex.getMessage();
        if (ex.getCause() != null) {
            errorDetails += " - Causa: " + ex.getCause().getMessage();
        }

        Map<String, Object> respuesta = new HashMap<>();
        respuesta.put("timestamp", LocalDateTime.now());
        respuesta.put("status", HttpStatus.BAD_REQUEST.value());
        respuesta.put("error", "Error de formato JSON");
        respuesta.put("mensaje", "El JSON enviado no es válido");
        respuesta.put("detalles", errorDetails);
        respuesta.put("path", "/api/v1");

        return new ResponseEntity<>(respuesta, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<Map<String, Object>> handleGeneralException(Exception ex) {
        log.error("Error inesperado: {}", ex.getMessage(), ex);
        Map<String, Object> respuesta = new HashMap<>();
        respuesta.put("timestamp", LocalDateTime.now());
        respuesta.put("status", HttpStatus.INTERNAL_SERVER_ERROR.value());
        respuesta.put("error", "Error inesperado");
        respuesta.put("mensaje", "Ha ocurrido un error inesperado en el servidor");
        respuesta.put("detalles", ex.getMessage());
        respuesta.put("path", "/api/v1");

        return new ResponseEntity<>(respuesta, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}