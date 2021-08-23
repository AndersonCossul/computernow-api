package br.unisinos.computernowapi.services.impl;

import br.unisinos.computernowapi.entities.ComputerEntity;
import br.unisinos.computernowapi.exceptions.ComputerNotFoundException;
import br.unisinos.computernowapi.repositories.ComputerRepository;
import br.unisinos.computernowapi.services.ComputerService;
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
public class ComputerServiceImpl implements ComputerService {
    private final ComputerRepository computerRepository;

    @Override
    public List<ComputerEntity> findAll() {
        log.debug("findAll()");
        return computerRepository.findAll();
    }

    @Override
    public ComputerEntity findById(Long id) throws ComputerNotFoundException {
        log.debug("findById(" + id + ")");
        Optional<ComputerEntity> entity = computerRepository.findById(id);
        if (entity.isEmpty()) {
            ComputerNotFoundException e = new ComputerNotFoundException(id);
            log.error(e.getMessage());
            throw e;
        }
        return entity.get();
    }

    @Override
    @Transactional
    public ComputerEntity update(ComputerEntity computerEntity) {
        log.debug("update(" + computerEntity + ")");
        return computerRepository.save(computerEntity);
    }

    @Override
    @Transactional
    public ComputerEntity create(ComputerEntity computerEntity) {
        log.debug("create(" + computerEntity + ")");
        return computerRepository.save(computerEntity);
    }

    @Override
    @Transactional
    public void delete(Long id) throws NoSuchElementException {
        log.debug("delete(" + id + ")");
        if (!computerRepository.existsById(id)) {
            ComputerNotFoundException e = new ComputerNotFoundException(id);
            log.error(e.getMessage());
            throw e;
        }
        computerRepository.deleteById(id);
    }
}
