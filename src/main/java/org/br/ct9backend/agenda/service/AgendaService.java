package org.br.ct9backend.agenda.service;

import org.br.ct9backend.agenda.model.Agenda;
import org.br.ct9backend.agenda.model.AgendaDTO;
import org.br.ct9backend.agenda.model.Tarefa;

import java.util.List;

public interface AgendaService {

    List<AgendaDTO> getDisciplinasCursando(String firebaseUid);

    List<Tarefa> getTarefas(String firebaseUid);

    Tarefa saveTarefa(String firebaseUid, Tarefa tarefa);

    Boolean deletarTarefa(Long tarefaId);

    Tarefa editarTarefa(Long tarefaId, Tarefa tarefa);
}
