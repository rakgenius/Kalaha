package com.rakgenius.kalaha.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Custome exception class for handling illegal moves while playing the game
 */
@ResponseStatus(HttpStatus.BAD_REQUEST)
public class KalahaGameIllegalMoveException extends RuntimeException {
    public KalahaGameIllegalMoveException() {
        super();
    }

    public KalahaGameIllegalMoveException(String message) {
        super(message);
    }

    public KalahaGameIllegalMoveException(String message, Throwable cause) {
        super(message, cause);
    }

    public KalahaGameIllegalMoveException(Throwable cause) {
        super(cause);
    }

    protected KalahaGameIllegalMoveException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
