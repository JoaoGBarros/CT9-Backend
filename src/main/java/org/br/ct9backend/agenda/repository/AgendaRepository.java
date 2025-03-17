package org.br.ct9backend.agenda.repository;

import org.br.ct9backend.agenda.model.Agenda;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AgendaRepository extends JpaRepository<Agenda, Long> {
    Optional<Agenda> findByTarefasId(Long tarefaId);
}
