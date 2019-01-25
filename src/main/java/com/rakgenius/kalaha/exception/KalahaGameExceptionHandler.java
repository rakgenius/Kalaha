package com.rakgenius.kalaha.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

/**
 * Common exception handlers which are implemented using the
 * controllerAdvice annotation.
 *
 * One handler for the general Game related exception and the other
 * for handling illegal moves while playing the game.
 */
@ControllerAdvice
public class KalahaGameExceptionHandler extends ResponseEntityExceptionHandler {
    @ExceptionHandler(KalahaGameException.class)
    public final ResponseEntity handleKalahaGameException(final KalahaGameException e) {
        ExceptionResponse response = ExceptionResponse.of(e.getMessage(), HttpStatus.NOT_FOUND);
        return ResponseEntity.status(response.getStatus()).body(response);
    }

    @ExceptionHandler(KalahaGameIllegalMoveException.class)
    public final ResponseEntity handleIllegalMoveException(final KalahaGameIllegalMoveException e) {
        ExceptionResponse response = ExceptionResponse.of(e.getMessage(), HttpStatus.BAD_REQUEST);
        return ResponseEntity.status(response.getStatus()).body(response);
    }
}
