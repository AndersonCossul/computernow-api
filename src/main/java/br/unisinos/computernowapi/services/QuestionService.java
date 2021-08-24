package br.unisinos.computernowapi.services;

import br.unisinos.computernowapi.entities.QuestionEntity;
import br.unisinos.computernowapi.exceptions.AnswerNotFoundException;
import br.unisinos.computernowapi.exceptions.QuestionNotFoundException;

import java.util.List;

public interface QuestionService {
    List<QuestionEntity> findAll();

    QuestionEntity findById(Long id) throws QuestionNotFoundException;

    QuestionEntity update(QuestionEntity questionEntity);

    QuestionEntity create(QuestionEntity questionEntity);

    void delete(Long id) throws QuestionNotFoundException;

    QuestionEntity relateAnswerToQuestion(Long questionId, Long answerId) throws QuestionNotFoundException, AnswerNotFoundException;

    QuestionEntity removeAnswerFromQuestion(Long questionId, Long answerId) throws QuestionNotFoundException, AnswerNotFoundException;
}
