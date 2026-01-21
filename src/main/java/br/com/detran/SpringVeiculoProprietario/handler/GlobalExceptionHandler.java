package br.com.detran.SpringVeiculoProprietario.handler;

import br.com.detran.SpringVeiculoProprietario.exception.BusinessException;
import br.com.detran.SpringVeiculoProprietario.exception.ResourceNotFoundException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.ConstraintViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.Instant;
import java.util.stream.Collectors;

@RestControllerAdvice
public class GlobalExceptionHandler {

    // ✅ Erros de validação no @RequestBody (@Valid)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ApiError> handleValidation(MethodArgumentNotValidException ex, HttpServletRequest request) {
        var status = HttpStatus.BAD_REQUEST;

        String message = ex.getBindingResult().getFieldErrors().stream()
                .map(err -> err.getField() + ": " + err.getDefaultMessage())
                .collect(Collectors.joining("; "));

        var body = new ApiError(
                Instant.now(),
                status.value(),
                status.getReasonPhrase(),
                message.isBlank() ? "Dados inválidos." : message,
                request.getRequestURI()
        );

        return ResponseEntity.status(status).body(body);
    }

    // ✅ Erros de validação no Path/RequestParam (ex: @Pattern no @PathVariable)
    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<ApiError> handleConstraintViolation(ConstraintViolationException ex, HttpServletRequest request) {
        var status = HttpStatus.BAD_REQUEST;

        String message = ex.getConstraintViolations().stream()
                .map(v -> v.getMessage())
                .collect(Collectors.joining("; "));

        var body = new ApiError(
                Instant.now(),
                status.value(),
                status.getReasonPhrase(),
                message.isBlank() ? "Parâmetros inválidos." : message,
                request.getRequestURI()
        );

        return ResponseEntity.status(status).body(body);
    }

    // ✅ JSON mal formado / erro de parse
    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<ApiError> handleJsonMalformed(HttpMessageNotReadableException ex, HttpServletRequest request) {
        var status = HttpStatus.BAD_REQUEST;

        var body = new ApiError(
                Instant.now(),
                status.value(),
                status.getReasonPhrase(),
                "JSON inválido ou mal formatado.",
                request.getRequestURI()
        );

        return ResponseEntity.status(status).body(body);
    }

    // ✅ Erros de regra de negócio (ex: CPF/CNPJ duplicado)
    @ExceptionHandler(BusinessException.class)
    public ResponseEntity<ApiError> handleBusiness(BusinessException ex, HttpServletRequest request) {
        var status = HttpStatus.CONFLICT;

        var body = new ApiError(
                Instant.now(),
                status.value(),
                status.getReasonPhrase(),
                ex.getMessage(),
                request.getRequestURI()
        );

        return ResponseEntity.status(status).body(body);
    }

    // ✅ Recurso não encontrado
    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ApiError> handleNotFound(ResourceNotFoundException ex, HttpServletRequest request) {
        var status = HttpStatus.NOT_FOUND;

        var body = new ApiError(
                Instant.now(),
                status.value(),
                status.getReasonPhrase(),
                ex.getMessage(),
                request.getRequestURI()
        );

        return ResponseEntity.status(status).body(body);
    }

    // ✅ Erro genérico (último caso)
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiError> handleGeneric(Exception ex, HttpServletRequest request) {
        var status = HttpStatus.INTERNAL_SERVER_ERROR;

        var body = new ApiError(
                Instant.now(),
                status.value(),
                status.getReasonPhrase(),
                "Erro inesperado.",
                request.getRequestURI()
        );

        return ResponseEntity.status(status).body(body);
    }
}
