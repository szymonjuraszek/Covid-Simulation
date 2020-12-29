package com.szymon.app.model;

import com.szymon.app.dto.SimulationDTO;
import lombok.AccessLevel;
import lombok.Data;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

@Entity
@Data
public class Simulation {

    @Id
    @GeneratedValue(generator = "ID_SEQ_1", strategy = GenerationType.SEQUENCE)
    @SequenceGenerator(name = "ID_SEQ_1", sequenceName = "ID_SEQUENCE_1", allocationSize = 1)
    @Setter(AccessLevel.NONE)
    private Long id;

    @Column(unique = true)
    private String simulationName;

    private Long populationSize;

    private Long initialNumberOfInfected;

    private Integer infectionRate;

    private Double dyingRate;

    private Integer numberOfDaysUntilRecovery;

    private Integer numberOfDaysUntilDeath;

    private Integer numberOfDaysForSimulation;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "population_state_record_id")
    private List<PopulationStateRecord> populationStateRecords;

    public void update(Simulation simulation) {

        this.setPopulationSize(simulation.getPopulationSize());
        this.setDyingRate(simulation.getDyingRate());
        this.setInfectionRate(simulation.getInfectionRate());
        this.setInitialNumberOfInfected(simulation.getInitialNumberOfInfected());
        this.setNumberOfDaysForSimulation(simulation.getNumberOfDaysForSimulation());
        this.setNumberOfDaysUntilDeath(simulation.getNumberOfDaysUntilDeath());
        this.setNumberOfDaysUntilRecovery(simulation.getNumberOfDaysUntilRecovery());
        this.setSimulationName(simulation.getSimulationName());

        this.getPopulationStateRecords().clear();
    }
}
