package com.huesped.huespedes.exception;

import java.time.LocalDateTime;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

        @ExceptionHandler(RecursoNoEncontradoException.class)
        public ResponseEntity<ErrorResponse> manejarNoEncontrado(
                        RecursoNoEncontradoException ex) {

                ErrorResponse error = new ErrorResponse(
                                LocalDateTime.now(),
                                HttpStatus.NOT_FOUND.value(),
                                "NOT_FOUND",
                                ex.getMessage());

                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                                .body(error);
        }

        @ExceptionHandler(RecursoDuplicadoException.class)
        public ResponseEntity<ErrorResponse> manejarDuplicado(
                        RecursoDuplicadoException ex) {

                ErrorResponse error = new ErrorResponse(
                                LocalDateTime.now(),
                                HttpStatus.CONFLICT.value(),
                                "CONFLICT",
                                ex.getMessage());

                return ResponseEntity.status(HttpStatus.CONFLICT)
                                .body(error);
        }

        @ExceptionHandler(MethodArgumentNotValidException.class)
        public ResponseEntity<ErrorResponse> manejarValidaciones(
                        MethodArgumentNotValidException ex) {

                String mensaje = ex.getBindingResult()
                                .getFieldError()
                                .getDefaultMessage();

                ErrorResponse error = new ErrorResponse(
                                LocalDateTime.now(),
                                HttpStatus.BAD_REQUEST.value(),
                                "BAD_REQUEST",
                                mensaje);

                return ResponseEntity.badRequest()
                                .body(error);
        }

        @ExceptionHandler(Exception.class)
        public ResponseEntity<ErrorResponse> manejarGeneral(
                        Exception ex) {

                ErrorResponse error = new ErrorResponse(
                                LocalDateTime.now(),
                                HttpStatus.INTERNAL_SERVER_ERROR.value(),
                                "INTERNAL_SERVER_ERROR",
                                ex.getMessage());

                return ResponseEntity.status(
                                HttpStatus.INTERNAL_SERVER_ERROR)
                                .body(error);
        }
}