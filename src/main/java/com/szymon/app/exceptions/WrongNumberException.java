package com.szymon.app.exceptions;

public class WrongNumberException extends RuntimeException {

    public WrongNumberException(String message) {
        super(message);
    }

    public WrongNumberException(String message, Throwable cause) {
        super(message,cause);
    }
}
