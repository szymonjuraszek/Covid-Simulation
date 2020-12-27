package com.szymon.app.exceptions;

import com.fasterxml.jackson.databind.JsonMappingException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.sql.Timestamp;

@RestControllerAdvice
public class SimulationErrorHandler {

    Logger logger = LoggerFactory.getLogger(SimulationErrorHandler.class);

    @ExceptionHandler({WrongNumberException.class, JsonMappingException.class, MethodArgumentNotValidException.class, SimulationNameNonUnique.class})
    public ResponseEntity<Object> handleCustomException(Exception e) {
        logger.error(e.getMessage());

        SimulationException simulationException = new SimulationException(
                e.getMessage(),
                new Timestamp(System.currentTimeMillis())
        );

        return new ResponseEntity<>(simulationException, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler({SimulationNotFound.class})
    public ResponseEntity<Object> handleSimulationNotFound(SimulationNotFound e) {
        logger.error(e.getMessage());

        SimulationException simulationException = new SimulationException(
                e.getMessage(),
                new Timestamp(System.currentTimeMillis())
        );

        return new ResponseEntity<>(simulationException, HttpStatus.NOT_FOUND);
    }
}
