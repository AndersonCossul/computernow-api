package br.unisinos.computernowapi.services.impl;

import br.unisinos.computernowapi.entities.AnswerEntity;
import br.unisinos.computernowapi.exceptions.AnswerNotFoundException;
import br.unisinos.computernowapi.exceptions.ComputerNotFoundException;
import br.unisinos.computernowapi.repositories.AnswerRepository;
import br.unisinos.computernowapi.services.AnswerService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class AnswerServiceImpl implements AnswerService {
    private final AnswerRepository answerRepository;

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
    public void delete(Long id) throws NoSuchElementException {
        log.debug("delete(" + id + ")");
        if (!answerRepository.existsById(id)) {
            ComputerNotFoundException e = new ComputerNotFoundException(id);
            log.error(e.getMessage());
            throw e;
        }
        answerRepository.deleteById(id);
    }
}
