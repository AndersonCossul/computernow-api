package br.unisinos.computernowapi.services;

import br.unisinos.computernowapi.entities.AnswerEntity;
import br.unisinos.computernowapi.exceptions.AnswerNotFoundException;

import java.util.List;

public interface AnswerService {
    List<AnswerEntity> findAll();

    AnswerEntity findById(Long id) throws AnswerNotFoundException;

    AnswerEntity update(AnswerEntity answerEntity);

    AnswerEntity create(AnswerEntity answerEntity);

    void delete(Long id) throws AnswerNotFoundException;
}
