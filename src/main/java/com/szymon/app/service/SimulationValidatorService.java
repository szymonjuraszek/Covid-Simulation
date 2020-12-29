package com.szymon.app.service;

import com.szymon.app.exceptions.WrongNumberException;
import com.szymon.app.model.Simulation;
import org.springframework.stereotype.Service;

@Service
public class SimulationValidatorService {

    public void valid(Simulation simulation) {
        if (simulation.getPopulationSize() < simulation.getInitialNumberOfInfected()) {
            throw new WrongNumberException("Field: 'populationSize' can't be lower than 'initialNumberOfInfected'");
        }

        if (simulation.getNumberOfDaysUntilDeath() > simulation.getNumberOfDaysUntilRecovery()) {
            throw new WrongNumberException("Field: 'numberOfDaysUntilRecovery' can't be lower than 'numberOfDaysUntilDeath'");
        }

        if (simulation.getDyingRate() <= 0.0 && simulation.getDyingRate() > 1.0) {
            throw new WrongNumberException("Field: 'dyingRate' must be between 0.0 and 1.0");
        }
    }
}
