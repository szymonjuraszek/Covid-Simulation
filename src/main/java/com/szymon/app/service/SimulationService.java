package com.szymon.app.service;

import com.szymon.app.exceptions.SimulationNameNonUnique;
import com.szymon.app.model.Simulation;
import com.szymon.app.repository.SimulationRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class SimulationService {

    private final SimulationRepository simulationRepository;

    public SimulationService(SimulationRepository simulationRepository) {
        this.simulationRepository = simulationRepository;
    }

    public void saveSimulation(Simulation simulation) {
        try {
            simulationRepository.save(simulation);
        } catch (Exception e) {
            throw new SimulationNameNonUnique("Simulation with name: '" + simulation.getSimulationName() + "' exist!");
        }
    }

    public Optional<Simulation> getSimulationByName(String name) {
        return simulationRepository.findSimulationByName(name);
    }

    public Optional<Simulation> getSimulationById(Long id) {
        return simulationRepository.findById(id);
    }

    public List<Simulation> getAllSimulations() {
        return simulationRepository.findAll();
    }

    public boolean deleteSimulation(Long id) {
        if(simulationRepository.existsById(id)) {
            simulationRepository.deleteById(id);
            return true;
        } else {
            return false;
        }
    }
}
