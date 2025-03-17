package org.br.ct9backend.agenda.model.auxTable;


import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.br.ct9backend.agenda.model.Agenda;
import org.br.ct9backend.agenda.model.Tarefa;
import org.br.ct9backend.aluno.model.Aluno;
import org.br.ct9backend.curso.model.SituacaoDisciplina;

@Entity
@Table(name = "agenda_tarefas")
@Getter
@Setter
public class AgendaTarefas {

    @Id
    private Long id;

    @ManyToOne
    @JoinColumn(name = "agenda_id", nullable = false)
    private Agenda agenda;

    @ManyToOne
    @JoinColumn(name = "tarefa_id", nullable = false)
    private Tarefa tarefa;
}
