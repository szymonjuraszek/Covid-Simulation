package com.szymon.app.exceptions;

import lombok.Data;
import java.sql.Timestamp;

@Data
public class SimulationException {
    private final String message;
    private final Timestamp timestamp;

    public SimulationException(String message, Timestamp timestamp) {
        this.message = message;
        this.timestamp = timestamp;
    }
}
