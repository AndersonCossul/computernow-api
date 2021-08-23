package br.unisinos.computernowapi.services;

import br.unisinos.computernowapi.entities.ComputerEntity;
import br.unisinos.computernowapi.exceptions.ComputerNotFoundException;

import java.util.List;

public interface ComputerService {
    List<ComputerEntity> findAll();

    ComputerEntity findById(Long id) throws ComputerNotFoundException;

    ComputerEntity update(ComputerEntity computerEntity);

    ComputerEntity create(ComputerEntity computerEntity);

    void delete(Long id) throws ComputerNotFoundException;
}
