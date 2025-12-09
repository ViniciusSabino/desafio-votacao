package com.desafiovotacao.presentation.exception;

import com.desafiovotacao.domain.exception.BusinessException;
import com.desafiovotacao.domain.exception.VotingException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

import java.util.List;
import java.util.stream.Collectors;

@ControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ExceptionResponse> handleMethodArgumentNotValidException(MethodArgumentNotValidException ex) {
        List<String> errors = ex.getBindingResult().getFieldErrors().stream().map(error -> error.getField() + ": " + error.getDefaultMessage()).collect(Collectors.toList());

        ExceptionResponse response = new ExceptionResponse("Validation error", errors, HttpStatus.BAD_REQUEST.toString());

        log.warn("Method Argument Not Valid Exception: {}", errors);

        return ResponseEntity.badRequest().body(response);
    }

    @ExceptionHandler(VotingException.class)
    public ResponseEntity<ExceptionResponse> handleVotingException(VotingException ex) {
        ExceptionResponse response = new ExceptionResponse(ex.getMessage(), String.valueOf(ex.status));

        log.warn("VotingException Exception: {}", ex.getMessage());

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
    }


    @ExceptionHandler(BusinessException.class)
    public ResponseEntity<ExceptionResponse> handleBusinessException(BusinessException ex) {
        ExceptionResponse response = new ExceptionResponse(ex.getMessage(), HttpStatus.BAD_REQUEST.toString());

        log.warn("Business Exception: {}", ex.getMessage());

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ExceptionResponse> handleAllExceptions(Exception ex, WebRequest request) {
        ExceptionResponse response = new ExceptionResponse("An unexpected error occurred", HttpStatus.INTERNAL_SERVER_ERROR.toString());

        log.error("An unexpected error occurred: ", ex);

        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
    }
}
