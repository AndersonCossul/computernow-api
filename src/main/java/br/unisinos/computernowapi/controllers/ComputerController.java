package br.unisinos.computernowapi.controllers;

import br.unisinos.computernowapi.controllers.requests.ComputerRequest;
import br.unisinos.computernowapi.controllers.responses.ComputerResponse;
import br.unisinos.computernowapi.entities.ComputerEntity;
import br.unisinos.computernowapi.exceptions.ComputerNotFoundException;
import br.unisinos.computernowapi.services.ComputerService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/v1/computer")
@RequiredArgsConstructor
@Tag(name = "Computer")
@Slf4j
public class ComputerController {
    private final ModelMapper modelMapper;
    private final ComputerService computerService;

    @GetMapping
    @Operation(summary = "List all computers")
    public List<ComputerResponse> findAll() {
        log.debug("findAll()");

        List<ComputerEntity> list = computerService.findAll();
        log.debug("list: " + list);

        List<ComputerResponse> convertedList = list.stream()
                .map(entity -> modelMapper.map(entity, ComputerResponse.class))
                .collect(Collectors.toList());
        log.debug("converted list: " + convertedList);

        return convertedList;
    }

    @GetMapping("/{id}")
    @Operation(summary = "Find a computer")
    public ComputerResponse findById(@PathVariable("id") Long id) {
        log.debug("findById(" + id + ")");

        ComputerEntity computerEntity;

        try {
            computerEntity = computerService.findById(id);
        } catch (ComputerNotFoundException e) {
            log.error(e.getMessage());
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        }

        log.debug("entity: " + computerEntity);

        return modelMapper.map(computerEntity, ComputerResponse.class);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "Create a new computer")
    public ComputerResponse create(@Valid @RequestBody ComputerRequest request) {
        log.debug("create(" + request + ")");

        ComputerEntity convertedRequest = modelMapper.map(request, ComputerEntity.class);
        log.debug("convertedRequest: " + convertedRequest);

        ComputerEntity newEntity = computerService.create(convertedRequest);
        log.debug("newEntity: " + newEntity);

        return modelMapper.map(newEntity, ComputerResponse.class);
    }

    @PutMapping("/update/{id}")
    @Operation(summary = "Update a computer")
    public ComputerResponse update(@PathVariable("id") Long id, @RequestBody ComputerRequest request) {
        log.debug(String.format("update(%s, %s)", id, request));

        ComputerEntity computerEntity = modelMapper.map(request, ComputerEntity.class);
        computerEntity.setId(id);
        log.debug("computerEntity: " + computerEntity);

        try {
            ComputerEntity savedComputerEntity = computerService.update(computerEntity);
            log.debug("savedComputerEntity: " + savedComputerEntity);
            return modelMapper.map(savedComputerEntity, ComputerResponse.class);
        } catch (ComputerNotFoundException e) {
            log.error(e.getMessage());
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        }
    }

    @DeleteMapping("/delete/{id}")
    @Operation(summary = "Delete a computer")
    public void delete(@PathVariable("id") Long id) {
        log.debug("delete(" + id + ")");

        try {
            computerService.delete(id);
        } catch (ComputerNotFoundException e) {
            log.error(e.getMessage());
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        }
    }
}
