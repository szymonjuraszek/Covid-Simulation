package com.szymon.app.controller;

import com.szymon.app.dto.SimulationDTO;
import com.szymon.app.exceptions.SimulationNotFound;
import com.szymon.app.model.Simulation;
import com.szymon.app.service.SimulationProcessService;
import com.szymon.app.service.SimulationService;
import com.szymon.app.service.SimulationValidatorService;
import org.modelmapper.ModelMapper;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
public class SimulationController {

    private final SimulationService simulationService;

    private final SimulationProcessService simulationProcessService;

    private final ModelMapper modelMapper;

    private final SimulationValidatorService simulationValidatorService;

    public SimulationController(
            SimulationService simulationService,
            SimulationProcessService simulationProcessService,
            ModelMapper modelMapper,
            SimulationValidatorService simulationValidatorService
    ) {
        this.simulationService = simulationService;
        this.simulationProcessService = simulationProcessService;
        this.modelMapper = modelMapper;
        this.simulationValidatorService = simulationValidatorService;
    }

    @PostMapping(path = "/simulation")
    public ResponseEntity<Object> createSimulation(@RequestBody @Valid SimulationDTO simulationDTO) {

        Simulation simulation = convertToEntity(simulationDTO);

        simulationProcessService.process(simulation);

        simulationService.saveSimulation(simulation);

        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(simulation.getId()).toUri();

        return ResponseEntity.created(location).build();
    }

    @GetMapping(path = "/simulation/{id}")
    public EntityModel<SimulationDTO> getSimulationById(@PathVariable Long id) {
        Optional<Simulation> simulation = simulationService.getSimulationById(id);

        if (simulation.isEmpty())
            throw new SimulationNotFound("simulation with id-" + id + " not exists!");

        EntityModel<SimulationDTO> resource = EntityModel.of(convertToDto(simulation.get()));

        WebMvcLinkBuilder linkTo = linkTo(methodOn(this.getClass()).getAllSimulations());

        resource.add(linkTo.withRel("all-simulations"));

        return resource;
    }

    @GetMapping(path = "/simulation")
    public EntityModel<SimulationDTO> getSimulationByName(@RequestParam String simulationName) {
        Optional<Simulation> simulation = simulationService.getSimulationByName(simulationName);

        if (simulation.isEmpty())
            throw new SimulationNotFound("simulation name-" + simulationName + " not exists!");

        EntityModel<SimulationDTO> resource = EntityModel.of(convertToDto(simulation.get()));

        WebMvcLinkBuilder linkTo = linkTo(methodOn(this.getClass()).getAllSimulations());

        resource.add(linkTo.withRel("all-simulations"));

        return resource;
    }

    @GetMapping("/simulations")
    public List<SimulationDTO> getAllSimulations() {
        return simulationService.getAllSimulations().stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    @DeleteMapping("/simulation/{id}")
    public ResponseEntity<Object> deleteSimulation(@PathVariable Long id) {
        boolean ifDelete = simulationService.deleteSimulation(id);

        if(!ifDelete) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PutMapping("/simulation/{id}")
    public ResponseEntity<Object> deleteSimulation(@RequestBody SimulationDTO simulationDTO, @PathVariable Long id) {
        Simulation simulation = convertToEntity(simulationDTO);

        Optional<Simulation> simulationFromDatabase = simulationService.getSimulationById(id);

        if(simulationFromDatabase.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        simulationFromDatabase.get().update(simulation);

        simulationProcessService.process(simulationFromDatabase.get());

        simulationService.updateSimulation(simulationFromDatabase.get());

        return ResponseEntity.noContent().build();
    }

    private Simulation convertToEntity(SimulationDTO simulationDTO) {
        Simulation simulation = modelMapper.map(simulationDTO, Simulation.class);
        simulation.setPopulationStateRecords(new ArrayList<>(simulation.getNumberOfDaysForSimulation()));

        simulationValidatorService.valid(simulation);

        return simulation;
    }

    private SimulationDTO convertToDto(Simulation simulation) {
        return modelMapper.map(simulation, SimulationDTO.class);
    }
}
