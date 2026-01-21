package br.com.detran.SpringVeiculoProprietario.handler;

import br.com.detran.SpringVeiculoProprietario.exception.BusinessException;
import br.com.detran.SpringVeiculoProprietario.exception.ResourceNotFoundException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.ConstraintViolationException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import java.time.Instant;
import java.util.stream.Collectors;

@RestControllerAdvice
public class GlobalExceptionHandler {

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

    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<ApiError> handleDataIntegrity(DataIntegrityViolationException ex, HttpServletRequest request) {
        var status = HttpStatus.CONFLICT;

        String message = "Placa ou RENAVAM já cadastrado.";

        Throwable cause = ex.getCause();

        while (cause != null) {
            if (cause instanceof org.hibernate.exception.ConstraintViolationException cve) {
                String constraint = cve.getConstraintName();

                if ("veiculo_placa_key".equals(constraint)) {
                    message = "Placa já cadastrada.";
                } else if ("veiculo_renavam_key".equals(constraint)) {
                    message = "RENAVAM já cadastrado.";
                }
                break;
            }
            cause = cause.getCause();
        }

        var body = new ApiError(
                Instant.now(),
                status.value(),
                status.getReasonPhrase(),
                message,
                request.getRequestURI()
        );

        return ResponseEntity.status(status).body(body);
    }

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

    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public ResponseEntity<ApiError> handleTypeMismatch(
            MethodArgumentTypeMismatchException ex,
            HttpServletRequest request
    ) {
        var status = HttpStatus.BAD_REQUEST;

        String message = "Parâmetro inválido.";

        if (ex.getName().equals("id")) {
            message = "ID inválido. Deve ser um número.";
        }

        var body = new ApiError(
                Instant.now(),
                status.value(),
                status.getReasonPhrase(),
                message,
                request.getRequestURI()
        );

        return ResponseEntity.status(status).body(body);
    }


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
