package br.unisinos.computernowapi.controllers;

import br.unisinos.computernowapi.controllers.requests.AnswerRequest;
import br.unisinos.computernowapi.controllers.responses.AnswerResponse;
import br.unisinos.computernowapi.entities.AnswerEntity;
import br.unisinos.computernowapi.exceptions.AnswerNotFoundException;
import br.unisinos.computernowapi.services.AnswerService;
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
@RequestMapping("/v1/answer")
@RequiredArgsConstructor
@Tag(name = "Answer")
@Slf4j
public class AnswerController {
    private final ModelMapper modelMapper;
    private final AnswerService answerService;

    @GetMapping
    @Operation(summary = "List all answers")
    public List<AnswerResponse> findAll() {
        log.debug("findAll()");

        List<AnswerEntity> list = answerService.findAll();
        log.debug("list: " + list);

        List<AnswerResponse> convertedList = list.stream()
                .map(entity -> modelMapper.map(entity, AnswerResponse.class))
                .collect(Collectors.toList());
        log.debug("converted list: " + convertedList);

        return convertedList;
    }

    @GetMapping("/{id}")
    @Operation(summary = "Find an answer")
    public AnswerResponse findById(@PathVariable("id") Long id) {
        log.debug("findById(" + id + ")");

        AnswerEntity answerEntity;

        try {
            answerEntity = answerService.findById(id);
        } catch (AnswerNotFoundException e) {
            log.error(e.getMessage());
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        }

        log.debug("entity: " + answerEntity);

        return modelMapper.map(answerEntity, AnswerResponse.class);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "Create a new answer")
    public AnswerResponse create(@Valid @RequestBody AnswerRequest request) {
        log.debug("create(" + request + ")");

        AnswerEntity convertedRequest = modelMapper.map(request, AnswerEntity.class);
        log.debug("convertedRequest: " + convertedRequest);

        AnswerEntity newEntity = answerService.create(convertedRequest);
        log.debug("newEntity: " + newEntity);

        return modelMapper.map(newEntity, AnswerResponse.class);
    }

    @PutMapping("/update/{id}")
    @Operation(summary = "Update an answer")
    public AnswerResponse update(@PathVariable("id") Long id, @Valid @RequestBody AnswerRequest request) {
        log.debug(String.format("update(%s, %s)", id, request));

        AnswerEntity answerEntity = modelMapper.map(request, AnswerEntity.class);
        answerEntity.setId(id);
        log.debug("answerEntity: " + answerEntity);

        try {
            AnswerEntity savedAnswerEntity = answerService.update(answerEntity);
            log.debug("savedAnswerEntity: " + savedAnswerEntity);
            return modelMapper.map(savedAnswerEntity, AnswerResponse.class);
        } catch (AnswerNotFoundException e) {
            log.error(e.getMessage());
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        }
    }

    @DeleteMapping("/delete/{id}")
    @Operation(summary = "Delete an answer")
    public void delete(@PathVariable("id") Long id) {
        log.debug("delete(" + id + ")");

        try {
            answerService.delete(id);
        } catch (AnswerNotFoundException e) {
            log.error(e.getMessage());
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        }
    }
}
