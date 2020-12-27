package com.szymon.app.exceptions;

public class SimulationNameNonUnique extends RuntimeException{

    public SimulationNameNonUnique(String message) {
        super(message);
    }

    public SimulationNameNonUnique(String message, Throwable cause) {
        super(message,cause);
    }
}
