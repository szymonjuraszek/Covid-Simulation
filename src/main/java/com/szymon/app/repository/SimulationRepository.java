package com.szymon.app.repository;

import com.szymon.app.model.Simulation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface SimulationRepository extends JpaRepository<Simulation, Long> {

    @Query(value = "select * from SIMULATION where SIMULATION_NAME = ?1", nativeQuery = true)
    Optional<Simulation> findSimulationByName(String simulationName);
}
