package org.br.ct9backend.aluno.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import org.br.ct9backend.agenda.model.Agenda;
import org.br.ct9backend.curso.model.Curso;
import org.br.ct9backend.curso.model.SituacaoDisciplina;
import org.br.ct9backend.security.auth.model.User;

import java.util.List;

@Entity
@Getter
@Setter
@Table
public class Aluno extends User {

    private Integer horasExt;

    private Integer horasCpl;

    @ManyToOne
    @JoinColumn(name = "curso_id")
    @NotNull
    private Curso curso;

    @OneToMany(mappedBy = "aluno", cascade = CascadeType.ALL)
    private List<SituacaoDisciplina> situacoesDisciplina;

    @OneToOne
    private Agenda agenda;
}
