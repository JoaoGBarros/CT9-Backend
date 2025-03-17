package org.br.ct9backend.curso.repository;

import org.br.ct9backend.curso.model.Requisito;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RequisitoRepository extends JpaRepository<Requisito, Long> {
}
