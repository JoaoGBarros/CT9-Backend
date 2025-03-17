package org.br.ct9backend.curso.repository;

import org.br.ct9backend.curso.model.DisciplinaOfertada;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DisciplinaOfertadaRepository extends JpaRepository<DisciplinaOfertada, Long> {

}
