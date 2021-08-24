package br.unisinos.computernowapi.services.impl;

import br.unisinos.computernowapi.entities.AnswerEntity;
import br.unisinos.computernowapi.entities.QuestionEntity;
import br.unisinos.computernowapi.exceptions.AnswerNotFoundException;
import br.unisinos.computernowapi.exceptions.QuestionNotFoundException;
import br.unisinos.computernowapi.repositories.QuestionRepository;
import br.unisinos.computernowapi.services.AnswerService;
import br.unisinos.computernowapi.services.QuestionService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class QuestionServiceImpl implements QuestionService {
    private final QuestionRepository questionRepository;
    private final AnswerService answerService;

    @Override
    public List<QuestionEntity> findAll() {
        log.debug("findAll()");
        return questionRepository.findAll();
    }

    @Override
    public QuestionEntity findById(Long id) throws QuestionNotFoundException {
        log.debug("findById(" + id + ")");
        Optional<QuestionEntity> entity = questionRepository.findById(id);
        if (entity.isEmpty()) {
            QuestionNotFoundException e = new QuestionNotFoundException(id);
            log.error(e.getMessage());
            throw e;
        }
        return entity.get();
    }

    @Override
    @Transactional
    public QuestionEntity update(QuestionEntity questionEntity) {
        log.debug("update(" + questionEntity + ")");
        return questionRepository.save(questionEntity);
    }

    @Override
    @Transactional
    public QuestionEntity create(QuestionEntity questionEntity) {
        log.debug("create(" + questionEntity + ")");
        return questionRepository.save(questionEntity);
    }

    @Override
    @Transactional
    public void delete(Long id) throws QuestionNotFoundException {
        log.debug("delete(" + id + ")");
        if (!questionRepository.existsById(id)) {
            QuestionNotFoundException e = new QuestionNotFoundException(id);
            log.error(e.getMessage());
            throw e;
        }
        questionRepository.deleteById(id);
    }

    @Override
    public QuestionEntity relateAnswerToQuestion(Long questionId, Long answerId) throws QuestionNotFoundException, AnswerNotFoundException {
        log.debug(String.format("relateAnswerToQuestion(%s, %s)", questionId, answerId));

        QuestionEntity questionEntity = findById(questionId);
        log.debug("questionEntity" + questionEntity);

        AnswerEntity answerEntity = answerService.findById(answerId);
        log.debug("answerEntity" + answerEntity);

        questionEntity.addAnswer(answerEntity);
        log.debug("questionEntity after added answer" + questionEntity);

        return questionRepository.save(questionEntity);
    }

    @Override
    public QuestionEntity removeAnswerFromQuestion(Long questionId, Long answerId) throws QuestionNotFoundException, AnswerNotFoundException {
        log.debug(String.format("removeAnswerFromQuestion(%s, %s)", questionId, answerId));

        QuestionEntity questionEntity = findById(questionId);
        log.debug("questionEntity" + questionEntity);

        AnswerEntity answerEntity = answerService.findById(answerId);
        log.debug("answerEntity" + answerEntity);

        questionEntity.removeAnswer(answerEntity);
        log.debug("questionEntity after added answer" + questionEntity);

        return questionRepository.save(questionEntity);
    }
}
