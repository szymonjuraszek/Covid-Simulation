package com.szymon.app.dto;

import lombok.Data;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.List;

@Data
public class SimulationDTO {

    @NotBlank
    @NotNull(message = "Field: 'simulationName' can't be null")
    private String simulationName;

    @Min(value = 1, message = "Population size must be at least 1")
    @NotNull(message = "Field: 'populationSize' can't be null")
    private Long populationSize;

    @Min(value = 1, message = "Initial number of infected must be at least 0")
    @NotNull(message = "Field: 'initialNumberOfInfected' can't be null")
    private Long initialNumberOfInfected;

    @Min(value = 1, message = "'infectionRate' must be at least 1")
    @NotNull(message = "Field: 'infectionRate' can't be null")
    private Integer infectionRate;

    @NotNull(message = "Field: 'dyingRate' can't be null")
    private Double dyingRate;

    @Min(value = 1, message = "'daysRecovery' must be at least 1")
    @NotNull(message = "Field: 'daysRecovery' can't be null")
    private Integer numberOfDaysUntilRecovery;

    @NotNull(message = "Field: 'numberOfDaysUntilDeath' can't be null")
    private Integer numberOfDaysUntilDeath;

    @Min(value = 1, message = "'numberOfDaysForSimulation' must be at least 1")
    @NotNull(message = "Field: 'numberOfDaysForSimulation' can't be null")
    private Integer numberOfDaysForSimulation;

    private List<PopulationStateRecordDto> populationStateRecords;
}
