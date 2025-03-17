package org.br.ct9backend.curso.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import org.br.ct9backend.aluno.model.Aluno;
import org.br.ct9backend.curso.model.enums.Situacao;

import java.io.Serializable;

@Entity
@Getter
@Setter
@Table
public class SituacaoDisciplina implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "aluno_id")
    @NotNull
    private Aluno aluno;

    @ManyToOne
    @JoinColumn(name = "disciplina_id")
    private Disciplina disciplina;

    @Enumerated(EnumType.STRING)
    @NotNull
    private Situacao situacao;

    @NotNull
    private Boolean avaliou;
}
