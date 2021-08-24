package br.unisinos.computernowapi.controllers;

import br.unisinos.computernowapi.controllers.requests.QuestionRelateRequest;
import br.unisinos.computernowapi.controllers.requests.QuestionRequest;
import br.unisinos.computernowapi.controllers.responses.QuestionResponse;
import br.unisinos.computernowapi.entities.QuestionEntity;
import br.unisinos.computernowapi.exceptions.AnswerNotFoundException;
import br.unisinos.computernowapi.exceptions.QuestionNotFoundException;
import br.unisinos.computernowapi.services.QuestionService;
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
@RequestMapping("/v1/question")
@RequiredArgsConstructor
@Tag(name = "Question")
@Slf4j
public class QuestionController {
    private final ModelMapper modelMapper;
    private final QuestionService questionService;

    @GetMapping
    @Operation(summary = "List all questions")
    public List<QuestionResponse> findAll() {
        log.debug("findAll()");

        List<QuestionEntity> list = questionService.findAll();
        log.debug("list: " + list);

        List<QuestionResponse> convertedList = list.stream()
                .map(entity -> modelMapper.map(entity, QuestionResponse.class))
                .collect(Collectors.toList());
        log.debug("converted list: " + convertedList);

        return convertedList;
    }

    @GetMapping("/{id}")
    @Operation(summary = "Find a question")
    public QuestionResponse findById(@PathVariable("id") Long id) {
        log.debug("findById(" + id + ")");

        QuestionEntity questionEntity;

        try {
            questionEntity = questionService.findById(id);
        } catch (QuestionNotFoundException e) {
            log.error(e.getMessage());
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        }

        log.debug("entity: " + questionEntity);

        return modelMapper.map(questionEntity, QuestionResponse.class);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "Create a new question")
    public QuestionResponse create(@Valid @RequestBody QuestionRequest request) {
        log.debug("create(" + request + ")");

        QuestionEntity convertedRequest = modelMapper.map(request, QuestionEntity.class);
        log.debug("convertedRequest: " + convertedRequest);

        QuestionEntity newEntity = questionService.create(convertedRequest);
        log.debug("newEntity: " + newEntity);

        return modelMapper.map(newEntity, QuestionResponse.class);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update a question")
    public QuestionResponse update(@PathVariable("id") Long id, @Valid @RequestBody QuestionRequest request) {
        log.debug(String.format("update(%s, %s)", id, request));

        QuestionEntity questionEntity = modelMapper.map(request, QuestionEntity.class);
        questionEntity.setId(id);
        log.debug("questionEntity: " + questionEntity);

        try {
            QuestionEntity savedQuestionEntity = questionService.update(questionEntity);
            log.debug("savedQuestionEntity: " + savedQuestionEntity);
            return modelMapper.map(savedQuestionEntity, QuestionResponse.class);
        } catch (QuestionNotFoundException e) {
            log.error(e.getMessage());
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        }
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete a question")
    public void delete(@PathVariable("id") Long id) {
        log.debug("delete(" + id + ")");

        try {
            questionService.delete(id);
        } catch (QuestionNotFoundException e) {
            log.error(e.getMessage());
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        }
    }

    @PostMapping("/{id}/answers/add")
    @Operation(summary = "Relate an answer to a question")
    public QuestionResponse relateAnswerToQuestion(@PathVariable("id") Long id, @Valid @RequestBody QuestionRelateRequest request) {
        log.debug(String.format("relateAnswerToQuestion(%s, %s)", id, request));

        try {
            QuestionEntity savedQuestionEntity = questionService.relateAnswerToQuestion(id, request.getAnswerId());
            log.debug("savedQuestionEntity" + savedQuestionEntity);

            QuestionResponse convertedQuestion = modelMapper.map(savedQuestionEntity, QuestionResponse.class);
            log.debug("convertedQuestion" + convertedQuestion);
            return convertedQuestion;
        } catch (QuestionNotFoundException | AnswerNotFoundException e) {
            log.error(e.getMessage());
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        }
    }

    @PostMapping("/{id}/answers/remove")
    @Operation(summary = "Remove an answer from a question")
    public QuestionResponse removeAnswerFromQuestion(@PathVariable("id") Long id, @Valid @RequestBody QuestionRelateRequest request) {
        log.debug(String.format("removeAnswerFromQuestion(%s, %s)", id, request));

        try {
            QuestionEntity savedQuestionEntity = questionService.removeAnswerFromQuestion(id, request.getAnswerId());
            log.debug("savedQuestionEntity" + savedQuestionEntity);

            QuestionResponse convertedQuestion = modelMapper.map(savedQuestionEntity, QuestionResponse.class);
            log.debug("convertedQuestion" + convertedQuestion);
            return convertedQuestion;
        } catch (QuestionNotFoundException | AnswerNotFoundException e) {
            log.error(e.getMessage());
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        }
    }
}
