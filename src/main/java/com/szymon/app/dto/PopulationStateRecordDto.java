package com.szymon.app.dto;

import lombok.Data;

@Data
public class PopulationStateRecordDto {

    private Long numberOfInfected;

    private Long numberOfSusceptible;

    private Long numberOfDied;

    private Long numberOfRecovered;
}
