package com.szymon.app.service;

import com.szymon.app.model.PopulationStateRecord;
import com.szymon.app.model.Simulation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class SimulationProcessService {

    Logger logger = LoggerFactory.getLogger(SimulationProcessService.class);

    public void process(Simulation simulation) {
        logger.info("First Record: ");

        PopulationStateRecord firstRecord = new PopulationStateRecord();

        firstRecord.setNumberOfInfected(simulation.getInitialNumberOfInfected());
        firstRecord.setNumberOfSusceptible(simulation.getPopulationSize() - simulation.getInitialNumberOfInfected());
        firstRecord.setDifferenceBetweenDaysInInfectedPeople(simulation.getInitialNumberOfInfected());

        simulation.getPopulationStateRecords().add(firstRecord);

        logger.info("Start Simulation:");

        for (int i = 1; i < Math.min(simulation.getNumberOfDaysUntilDeath(), simulation.getNumberOfDaysForSimulation()); i++) {
            PopulationStateRecord record = new PopulationStateRecord();
            Long newInfectedPeople = simulation.getPopulationStateRecords().get(i - 1).getNumberOfInfected() * simulation.getInfectionRate();

            if (newInfectedPeople <= simulation.getPopulationStateRecords().get(i - 1).getNumberOfSusceptible()) {
                record.setNumberOfInfected(newInfectedPeople + simulation.getPopulationStateRecords().get(i - 1).getNumberOfInfected());
                record.setNumberOfSusceptible(simulation.getPopulationStateRecords().get(i - 1).getNumberOfSusceptible() - newInfectedPeople);
            } else {
                record.setNumberOfInfected(
                        simulation.getPopulationStateRecords().get(i - 1).getNumberOfInfected() +
                                simulation.getPopulationStateRecords().get(i - 1).getNumberOfSusceptible()
                );
                record.setNumberOfSusceptible(0L);
            }

            record.setDifferenceBetweenDaysInInfectedPeople(
                    record.getNumberOfInfected() - simulation.getPopulationStateRecords().get(i - 1).getNumberOfInfected());

            simulation.getPopulationStateRecords().add(record);
        }

        for (int i = simulation.getNumberOfDaysUntilDeath(); i < simulation.getNumberOfDaysForSimulation(); i++) {
            PopulationStateRecord record = new PopulationStateRecord();

            Long pastInfectedPeople = simulation.getPopulationStateRecords().get(i - simulation.getNumberOfDaysUntilDeath()).getDifferenceBetweenDaysInInfectedPeople() -
                    (simulation.getPopulationStateRecords().get(i - simulation.getNumberOfDaysUntilDeath()).getNumberOfDied() -
                            simulation.getPopulationStateRecords().get(previousIndex(i, simulation.getNumberOfDaysUntilDeath())).getNumberOfDied()) -
                    (simulation.getPopulationStateRecords().get(i - simulation.getNumberOfDaysUntilDeath()).getNumberOfRecovered() -
                            simulation.getPopulationStateRecords().get(previousIndex(i, simulation.getNumberOfDaysUntilDeath())).getNumberOfRecovered());
            if (pastInfectedPeople < 0L) {
                pastInfectedPeople = 0L;
            }
            record.setNumberOfDied((long) (pastInfectedPeople * simulation.getDyingRate() + simulation.getPopulationStateRecords().get(i - 1).getNumberOfDied()));

            Long peopleToRecover = 0L;
            if (simulation.getNumberOfDaysUntilRecovery() <= i) {
                if (simulation.getPopulationStateRecords().get(i - simulation.getNumberOfDaysUntilRecovery()).getDifferenceBetweenDaysInInfectedPeople() > 0L) {
                    peopleToRecover = simulation.getPopulationStateRecords().get(i - simulation.getNumberOfDaysUntilRecovery()).getDifferenceBetweenDaysInInfectedPeople() -
                            (simulation.getPopulationStateRecords().get(i - (simulation.getNumberOfDaysUntilRecovery() - simulation.getNumberOfDaysUntilDeath())).getNumberOfDied() -
                                    simulation.getPopulationStateRecords().get(i - (simulation.getNumberOfDaysUntilRecovery() - simulation.getNumberOfDaysUntilDeath()) - 1).getNumberOfDied()
                            );
                }
                record.setNumberOfRecovered(peopleToRecover + simulation.getPopulationStateRecords().get(i - 1).getNumberOfRecovered());
            }

            Long newInfectedPeople = simulation.getPopulationStateRecords().get(i - 1).getNumberOfInfected() * simulation.getInfectionRate() - (long) (pastInfectedPeople * simulation.getDyingRate());
            if (newInfectedPeople <= simulation.getPopulationStateRecords().get(i - 1).getNumberOfSusceptible()) {
                record.setNumberOfInfected(
                        newInfectedPeople + simulation.getPopulationStateRecords().get(i - 1).getNumberOfInfected());
                record.setNumberOfSusceptible(simulation.getPopulationStateRecords().get(i - 1).getNumberOfSusceptible() - newInfectedPeople - (long) (pastInfectedPeople * simulation.getDyingRate()));
            } else {
                record.setNumberOfInfected(
                        simulation.getPopulationSize() -
                                record.getNumberOfDied() -
                                record.getNumberOfRecovered()
                );
                record.setNumberOfSusceptible(0L);
            }
            record.setDifferenceBetweenDaysInInfectedPeople(
                    record.getNumberOfInfected() - simulation.getPopulationStateRecords().get(i - 1).getNumberOfInfected());

            simulation.getPopulationStateRecords().add(record);
        }

        logger.info("Ended simulation");
    }

    private int previousIndex(int i, int numberOfDaysUntilDeath) {
        int index = i - numberOfDaysUntilDeath - 1;
        if (index < 0) {
            return 0;
        } else {
            return index;
        }
    }
}
