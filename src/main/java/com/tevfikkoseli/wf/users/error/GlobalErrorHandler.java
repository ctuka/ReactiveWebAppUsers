package com.tevfikkoseli.wf.users.error;

import org.springframework.dao.DuplicateKeyException;
import org.springframework.http.HttpStatus;
import org.springframework.web.ErrorResponse;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import reactor.core.publisher.Mono;

@RestControllerAdvice
public class GlobalErrorHandler {


    @ExceptionHandler(DuplicateKeyException.class)
    Mono<ErrorResponse> handleDublicateKeyException (DuplicateKeyException exception) {
        return Mono.just(ErrorResponse.builder(exception, HttpStatus.CONFLICT, exception.getMessage()).build());

    }

    @ExceptionHandler(IllegalArgumentException.class)
    Mono<ErrorResponse> handleGeneralException (IllegalArgumentException exception) {
        return Mono.just(ErrorResponse.builder(exception, HttpStatus.BAD_REQUEST, exception.getMessage()).build());

    }

    @ExceptionHandler(Exception.class)
    Mono<ErrorResponse> handleGeneralException (DuplicateKeyException exception) {
        return Mono.just(ErrorResponse.builder(exception, HttpStatus.INTERNAL_SERVER_ERROR, exception.getMessage()).build());

    }
}
