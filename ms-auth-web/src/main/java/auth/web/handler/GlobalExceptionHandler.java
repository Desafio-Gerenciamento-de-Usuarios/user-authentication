package auth.web.handler;

import auth.domain.exception.*;
import auth.web.handler.dto.ErrorResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.servlet.NoHandlerFoundException;

import java.time.Instant;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(ResponseStatusException.class)
    public ResponseEntity<ErrorResponse> handleResponseStatusException(ResponseStatusException ex) {
        ErrorResponse error = new ErrorResponse(
                Instant.now(),
                ex.getStatusCode().value(),
                ex.getReason(),
                ex.getMessage()
        );
        return ResponseEntity.status(ex.getStatusCode()).body(error);
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<ErrorResponse> handleIllegalArgument(IllegalArgumentException ex) {
        ErrorResponse error = new ErrorResponse(
                Instant.now(),
                HttpStatus.BAD_REQUEST.value(),
                ex.getMessage(),
                ex.getLocalizedMessage()
        );
        return ResponseEntity.badRequest().body(error);
    }

    @ExceptionHandler(ConflictException.class)
    public ResponseEntity<ErrorResponse> handleIllegalState(ConflictException ex) {
        ErrorResponse error = new ErrorResponse(
                Instant.now(),
                HttpStatus.CONFLICT.value(),
                ex.getMessage(),
                ex.getLocalizedMessage()
        );
        return ResponseEntity.status(HttpStatus.CONFLICT).body(error);
    }

    // ========== NOVOS HANDLERS PARA JWT ==========

    @ExceptionHandler(TokenExpiredException.class)
    public ResponseEntity<ErrorResponse> handleTokenExpired(TokenExpiredException ex) {
        ErrorResponse error = new ErrorResponse(
                Instant.now(),
                HttpStatus.UNAUTHORIZED.value(),
                "Token expirado",
                ex.getMessage()
        );
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(error);
    }

    @ExceptionHandler(InvalidTokenException.class)
    public ResponseEntity<ErrorResponse> handleInvalidToken(InvalidTokenException ex) {
        ErrorResponse error = new ErrorResponse(
                Instant.now(),
                HttpStatus.UNAUTHORIZED.value(),
                "Token inválido",
                ex.getMessage()
        );
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(error);
    }

    @ExceptionHandler(JwtValidationException.class)
    public ResponseEntity<ErrorResponse> handleJwtValidation(JwtValidationException ex) {
        ErrorResponse error = new ErrorResponse(
                Instant.now(),
                HttpStatus.BAD_REQUEST.value(),
                "Erro na validação do token",
                ex.getMessage()
        );
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
    }

    // ========== HANDLERS EXISTENTES ==========

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleGenericException(Exception ex) {
        ErrorResponse error = new ErrorResponse(
                Instant.now(),
                HttpStatus.INTERNAL_SERVER_ERROR.value(),
                "Ocorreu um erro interno no servidor",
                ex.getMessage()
        );
        ex.printStackTrace();
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(error);
    }

    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<ErrorResponse> handleAccessDenied(Exception ex) {
        ErrorResponse error = new ErrorResponse(
                Instant.now(),
                HttpStatus.UNAUTHORIZED.value(),
                "Acesso negado",
                ex.getMessage()
        );
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(error);
    }

    @ExceptionHandler(NoHandlerFoundException.class)
    public ResponseEntity<ErrorResponse> handleNotFound(NoHandlerFoundException ex) {
        ErrorResponse error = new ErrorResponse(
                Instant.now(),
                HttpStatus.NOT_FOUND.value(),
                "Recurso não encontrado: " + ex.getRequestURL(),
                ex.getMessage()
        );
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error);
    }
}