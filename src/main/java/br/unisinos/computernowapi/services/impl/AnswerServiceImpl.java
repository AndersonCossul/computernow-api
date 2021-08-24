package br.unisinos.computernowapi.services.impl;

import br.unisinos.computernowapi.entities.AnswerEntity;
import br.unisinos.computernowapi.entities.ComputerEntity;
import br.unisinos.computernowapi.exceptions.AnswerNotFoundException;
import br.unisinos.computernowapi.exceptions.ComputerNotFoundException;
import br.unisinos.computernowapi.repositories.AnswerRepository;
import br.unisinos.computernowapi.services.AnswerService;
import br.unisinos.computernowapi.services.ComputerService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class AnswerServiceImpl implements AnswerService {
    private final AnswerRepository answerRepository;
    private final ComputerService computerService;

    @Override
    public List<AnswerEntity> findAll() {
        log.debug("findAll()");
        return answerRepository.findAll();
    }

    @Override
    public AnswerEntity findById(Long id) throws AnswerNotFoundException {
        log.debug("findById(" + id + ")");
        Optional<AnswerEntity> entity = answerRepository.findById(id);
        if (entity.isEmpty()) {
            AnswerNotFoundException e = new AnswerNotFoundException(id);
            log.error(e.getMessage());
            throw e;
        }
        return entity.get();
    }

    @Override
    @Transactional
    public AnswerEntity update(AnswerEntity answerEntity) {
        log.debug("update(" + answerEntity + ")");
        return answerRepository.save(answerEntity);
    }

    @Override
    @Transactional
    public AnswerEntity create(AnswerEntity answerEntity) {
        log.debug("create(" + answerEntity + ")");
        return answerRepository.save(answerEntity);
    }

    @Override
    @Transactional
    public void delete(Long id) throws AnswerNotFoundException {
        log.debug("delete(" + id + ")");
        if (!answerRepository.existsById(id)) {
            AnswerNotFoundException e = new AnswerNotFoundException(id);
            log.error(e.getMessage());
            throw e;
        }
        answerRepository.deleteById(id);
    }

    @Override
    public AnswerEntity relateComputerToAnswer(Long answerId, Long computerId) throws AnswerNotFoundException, ComputerNotFoundException {
        log.debug(String.format("relateComputerToAnswer(%s, %s)", answerId, computerId));

        AnswerEntity answerEntity = findById(answerId);
        log.debug("answerEntity" + answerEntity);

        ComputerEntity computerEntity = computerService.findById(computerId);
        log.debug("computerEntity" + computerEntity);

        answerEntity.addComputer(computerEntity);
        log.debug("answerEntity after added computer" + answerEntity);

        return answerRepository.save(answerEntity);
    }

    @Override
    public AnswerEntity removeComputerFromAnswer(Long answerId, Long computerId) throws AnswerNotFoundException, ComputerNotFoundException {
        log.debug(String.format("removeComputerFromAnswer(%s, %s)", answerId, computerId));

        AnswerEntity answerEntity = findById(answerId);
        log.debug("answerEntity" + answerEntity);

        ComputerEntity computerEntity = computerService.findById(computerId);
        log.debug("computerEntity" + computerEntity);

        answerEntity.removeComputer(computerEntity);
        log.debug("answerEntity after removal computer" + answerEntity);

        return answerRepository.save(answerEntity);
    }
}
