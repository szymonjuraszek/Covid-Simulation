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
    }
}
