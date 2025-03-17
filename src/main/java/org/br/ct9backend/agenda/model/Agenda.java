package org.br.ct9backend.agenda.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.br.ct9backend.curso.model.SituacaoDisciplina;

import java.io.Serializable;
import java.util.List;

@Getter
@Setter
@Table
@Entity
public class Agenda implements Serializable {
    @Id
    @GeneratedValue
    private Long id;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Tarefa> tarefas;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    private List<AgendaDisciplinas> conteudo;
}
