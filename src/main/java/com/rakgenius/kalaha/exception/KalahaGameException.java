package com.rakgenius.kalaha.exception;

/*
Custom exception handler to handle the Runtime exception for the game
 */
public class KalahaGameException extends RuntimeException {
    public KalahaGameException() {
        super();
    }

    public KalahaGameException(String message) {
        super(message);
    }

    public KalahaGameException(String message, Throwable cause) {
        super(message, cause);
    }

    public KalahaGameException(Throwable cause) {
        super(cause);
    }

    protected KalahaGameException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
