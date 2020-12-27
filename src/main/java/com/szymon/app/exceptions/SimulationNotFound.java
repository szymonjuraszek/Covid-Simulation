package com.szymon.app.exceptions;

public class SimulationNotFound extends RuntimeException {

    public SimulationNotFound(String message) {
        super(message);
    }

    public SimulationNotFound(String message, Throwable cause) {
        super(message,cause);
    }
}
