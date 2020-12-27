package com.szymon.app.model;

import lombok.AccessLevel;
import lombok.Data;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Data
public class PopulationStateRecord {
    @Id
    @GeneratedValue(generator = "ID_SEQ_2", strategy = GenerationType.SEQUENCE)
    @SequenceGenerator(name = "ID_SEQ_2", sequenceName = "ID_SEQUENCE_2",allocationSize=1)
    @Setter(AccessLevel.NONE)
    private Long id;

    private Long numberOfInfected;

    private Long numberOfSusceptible;

    private Long numberOfDied;

    private Long numberOfRecovered;

    transient private Long differenceBetweenDaysInInfectedPeople;

    public PopulationStateRecord() {
        this.numberOfInfected = 0L;
        this.numberOfSusceptible = 0L;
        this.numberOfDied = 0L;
        this.numberOfRecovered = 0L;
        this.differenceBetweenDaysInInfectedPeople = 0L;
    }
}
