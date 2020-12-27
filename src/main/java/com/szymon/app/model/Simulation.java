package com.szymon.app.model;

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
    @SequenceGenerator(name = "ID_SEQ_1", sequenceName = "ID_SEQUENCE_1",allocationSize=1)
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

    @OneToMany(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "population_state_record_id")
    private List<PopulationStateRecord> populationStateRecords;
}
