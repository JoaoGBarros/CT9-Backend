package org.br.ct9backend.agenda.repository;

import org.br.ct9backend.agenda.model.Tarefa;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TarefaRepository extends JpaRepository<Tarefa,Long> {
}
