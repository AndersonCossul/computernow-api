package br.unisinos.computernowapi.repositories;

import br.unisinos.computernowapi.entities.ComputerEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ComputerRepository extends JpaRepository<ComputerEntity, Long> {
}
