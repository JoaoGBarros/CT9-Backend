package org.br.ct9backend.agenda.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import org.br.ct9backend.curso.model.Disciplina;

import java.io.Serializable;
import java.util.List;

@Entity
@Table
@Getter
@Setter
public class AgendaDisciplinas implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @NotNull
    private Disciplina disciplina;

    @OneToMany(cascade = CascadeType.ALL)
    private List<Falta> faltas;

    @OneToMany(cascade = CascadeType.ALL)
    private List<Avaliacao> avaliacoes;

}
