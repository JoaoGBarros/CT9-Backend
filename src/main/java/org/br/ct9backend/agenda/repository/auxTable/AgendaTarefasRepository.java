package org.br.ct9backend.agenda.repository.auxTable;

import org.br.ct9backend.agenda.model.auxTable.AgendaTarefas;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AgendaTarefasRepository extends JpaRepository<AgendaTarefas, Long> {

    void deleteByTarefaId(Long tarefaId);
}
